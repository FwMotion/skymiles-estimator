@file:Suppress("unused")

import kotlin.math.max

@JsName("calculateItineraryEarnings")
fun calculateItineraryEarnings(
    itinerary: Itinerary,
    estimateForMarketedAirline: Airline? = null
): CalculatedItineraryEarnings {

    val creditCardBonusRule =
        itinerary.creditCard.earningRules[itinerary.soldBy] ?: itinerary.creditCard.defaultEarningRules

    // TODO: Figure out how to handle the following: When connecting without changing planes (same flight number),
    //       there's some weirdness in the estimated earnings calculations that Delta provides in their booking
    //       flow. I believe multiple segments may be joined together in some way until a passenger actually deplanes,
    //       but will need to confirm. Could also count as an exception fare too, since MQMs change drastically? Then
    //       again, MQDs all stay identical at price
    // DL339 ATL->SEA (fare class Y) -- 2181.0 miles (according to booking flow for other Delta flights)
    // DL287 SEA->ICN (fare class Y) -- 5217.0 miles (according to booking flow for other Delta flights)
    // DL287 ICN->PVG (fare class Y) -- ???
    // $6290.00 + $210.00 + $34.30 totals to 32,500Miles 4,802MQMs $6,500MQDs
    //
    // DL287 SEA->ICN (fare class Y) -- 5217.0 miles (according to booking flow for other Delta flights)
    // DL287 ICN->PVG (fare class Y) -- ???
    // $6290.00 + $210.00 + $29.80 totals to 32,500Miles 1,530MQMs $6,500MQDs
    //
    // DL339 ATL->SEA (fare class J) -- 2181.0 miles (according to booking flow for other Delta flights)
    // DL287 SEA->ICN (fare class J) -- 5217.0 miles (according to booking flow for other Delta flights)
    // DL287 ICN->PVG (fare class J) -- ???
    // $14,426.00 + $410.00 + $34.30 totals to 74,180Miles 6,402MQMs $14,836MQDs
    //
    // DL287 SEA->ICN (fare class J) -- 5217.0 miles (according to booking flow for other Delta flights)
    // DL287 ICN->PVG (fare class J) -- ???
    // $14,426.00 + $410.00 + $34.30 totals to 74,180Miles 2,040MQMs $14,836MQDs
    //
    // SEA->PVG is 5721.732 miles according to https://www.airmilescalculator.com/distance/pvg-to-sea/
    // ICN->PVG is 509.613 according to https://www.airmilescalculator.com/distance/pvg-to-icn/
    val segments = itinerary.segments

    val calculatedSegments = segments.map { segment ->
        val segmentDistance: DistanceMiles = segment.distance
        val distanceRatio = segmentDistance / itinerary.totalDistance
        val segmentEligibleCost: USD = distanceRatio * itinerary.earningEligibleCost
        val segmentTotalCost: USD = distanceRatio * itinerary.totalCost

        // TODO: Map fare classes when earning is according to different airline?
        val earningAirline = estimateForMarketedAirline
            ?: earningAirlineOverride[segment.marketedBy]?.invoke(segment)
            ?: segment.marketedBy

        val earningRules = earningAirline?.fareRules?.get(segment.fareClass)
        val minimumMQMs = earningAirline?.minimumMedallionQualificationMiles ?: 0
        val segmentEarnsMiles = earningAirline?.marketedByEarnPredicate?.invoke(segment) ?: false
            && segment.operatedBy?.operatedByEarnPredicate?.invoke(segment) ?: false

        if (distanceRatio.isNaN() || earningRules == null || !segmentEarnsMiles) {
            return@map CalculatedSegmentEarning(
                origin = segment.origin,
                destination = segment.destination,
                fareClass = segment.fareClass,
                marketedBy = segment.marketedBy,
                marketedFlightNumber = segment.marketedFlightNumber,
                ticketedBy = segment.ticketedBy,
                operatedBy = segment.operatedBy,
                distance = segmentDistance,
                segmentEarningEligibleCost = segmentEligibleCost,
                segmentTotalCost = segmentTotalCost,
                baseMiles = 0,
                fareClassBonusMiles = 0,
                medallionStatusBonusMiles = 0,
                creditCardBonusMiles = if (segmentTotalCost.isNaN()) 0 else creditCardBonusRule(segmentTotalCost),
                medallionQualificationMiles = 0,
                medallionQualificationSegments = 0,
                medallionQualificationDollars = 0
            )
        }

        val baseMiles = earningRules.baseMiles(segmentEligibleCost, segmentDistance)

        val medallionStatusBonusMiles = if (earningRules.medallionMileageBonus) {
            itinerary.medallionStatus.earnBonus(baseMiles)
        } else {
            0
        }

        var medallionQualificationMiles = earningRules.medallionQualificationMiles(segmentEligibleCost, segmentDistance)
        if (medallionQualificationMiles > 0) {
            medallionQualificationMiles = max(
                medallionQualificationMiles,
                minimumMQMs
            )
        }

        return@map CalculatedSegmentEarning(
            origin = segment.origin,
            destination = segment.destination,
            fareClass = segment.fareClass,
            marketedBy = segment.marketedBy,
            marketedFlightNumber = segment.marketedFlightNumber ?: 0,
            ticketedBy = segment.ticketedBy,
            operatedBy = segment.operatedBy,
            distance = segmentDistance,
            segmentEarningEligibleCost = segmentEligibleCost,
            segmentTotalCost = segmentTotalCost,

            baseMiles = baseMiles,
            fareClassBonusMiles = earningRules.fareClassBonusMiles(segmentEligibleCost, segmentDistance),
            medallionStatusBonusMiles = medallionStatusBonusMiles,
            creditCardBonusMiles = creditCardBonusRule(segmentTotalCost),
            medallionQualificationMiles = medallionQualificationMiles,
            medallionQualificationSegments = earningRules.medallionQualificationSegments(
                segmentEligibleCost,
                segmentDistance
            ),
            medallionQualificationDollars = earningRules.medallionQualificationDollars(
                segmentEligibleCost,
                segmentDistance
            )
        )
    }.toTypedArray()

    return CalculatedItineraryEarnings(
        medallionStatus = itinerary.medallionStatus,
        segments = calculatedSegments,
        soldBy = itinerary.soldBy,
        baseCost = itinerary.baseCost,
        airlineFees = itinerary.airlineFees ?: 0.0,
        governmentFeesAndTaxes = itinerary.governmentFeesAndTaxes ?: 0.0,
        creditCard = itinerary.creditCard
    )
}
