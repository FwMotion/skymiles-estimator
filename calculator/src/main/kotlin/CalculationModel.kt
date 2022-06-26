@file:Suppress("ObjectPropertyName", "unused", "MemberVisibilityCanBePrivate", "DuplicatedCode")

import kotlin.math.roundToInt

typealias DistanceMiles = Double
typealias USD = Double
typealias AwardMiles = Int
typealias MedallionQualificationMiles = Int
typealias MedallionQualificationSegments = Int
typealias MedallionQualificationDollars = Int

typealias AwardMilesEarningRule = (dollars: USD, milesFlown: DistanceMiles) -> AwardMiles
typealias MedallionBonusEarningRule = (baseMiles: AwardMiles) -> AwardMiles
typealias CreditCardBonusEarningRule = (dollars: USD) -> AwardMiles

val awardBonus000Pct: MedallionBonusEarningRule = { 0 }
val awardBonus040Pct: MedallionBonusEarningRule = { baseMiles -> (baseMiles.toDouble() * 0.4).roundToInt() }
val awardBonus060Pct: MedallionBonusEarningRule = { baseMiles -> (baseMiles.toDouble() * 0.6).roundToInt() }
val awardBonus080Pct: MedallionBonusEarningRule = { baseMiles -> (baseMiles.toDouble() * 0.8).roundToInt() }
val awardBonus120Pct: MedallionBonusEarningRule = { baseMiles -> (baseMiles.toDouble() * 1.2).roundToInt() }

val creditBonus000Pct: CreditCardBonusEarningRule = { 0 }
val creditBonus100Pct: CreditCardBonusEarningRule = { dollars: USD -> dollars.roundToInt() }
val creditBonus150Pct: CreditCardBonusEarningRule =
    { dollars: USD -> (dollars.roundToInt().toDouble() * 1.5).roundToInt() }
val creditBonus200Pct: CreditCardBonusEarningRule = { dollars: USD -> dollars.roundToInt() * 2 }
val creditBonus300Pct: CreditCardBonusEarningRule = { dollars: USD -> dollars.roundToInt() * 3 }
val creditBonus500Pct: CreditCardBonusEarningRule = { dollars: USD -> dollars.roundToInt() * 5 }

fun creditBonus150PctWithThreshold(threshold: USD): CreditCardBonusEarningRule {
    return { dollars: USD ->
        if (dollars >= threshold) {
            (dollars.roundToInt().toDouble() * 1.5).roundToInt()
        } else {
            dollars.roundToInt()
        }
    }
}

val _500pctUSDs_ = { dollars: USD, _: DistanceMiles -> (dollars * 5.0).roundToInt() }
val _100pctUSDs_ = { dollars: USD, _: DistanceMiles -> dollars.roundToInt() }
val _200pctMiles = { _: USD, milesFlown: DistanceMiles -> (milesFlown * 2).roundToInt() }
val _150pctMiles = { _: USD, milesFlown: DistanceMiles -> (milesFlown * 1.5).roundToInt() }
val _125pctMiles = { _: USD, milesFlown: DistanceMiles -> (milesFlown * 1.25).roundToInt() }
val _100pctMiles = { _: USD, milesFlown: DistanceMiles -> milesFlown.roundToInt() }
val _075pctMiles = { _: USD, milesFlown: DistanceMiles -> (milesFlown * 0.75).roundToInt() }
val _060pctMiles = { _: USD, milesFlown: DistanceMiles -> (milesFlown * 0.6).roundToInt() }
val _050pctMiles = { _: USD, milesFlown: DistanceMiles -> (milesFlown * 0.5).roundToInt() }
val _040pctMiles = { _: USD, milesFlown: DistanceMiles -> (milesFlown * 0.4).roundToInt() }
val _030pctMiles = { _: USD, milesFlown: DistanceMiles -> (milesFlown * 0.3).roundToInt() }
val _025pctMiles = { _: USD, milesFlown: DistanceMiles -> (milesFlown * 0.25).roundToInt() }
val _020pctMiles = { _: USD, milesFlown: DistanceMiles -> (milesFlown * 0.2).roundToInt() }
val _015pctMiles = { _: USD, milesFlown: DistanceMiles -> (milesFlown * 0.15).roundToInt() }
val _010pctMiles = { _: USD, milesFlown: DistanceMiles -> (milesFlown * 0.1).roundToInt() }
val _005pctMiles = { _: USD, milesFlown: DistanceMiles -> (milesFlown * 0.05).roundToInt() }
val _000earnings = { _: USD, _: DistanceMiles -> 0 }
val _1segment = { _: USD, _: DistanceMiles -> 1 }
val _0segment = { _: USD, _: DistanceMiles -> 0 }


data class FlightEarningRules(
    val baseMiles: AwardMilesEarningRule,
    val fareClassBonusMiles: (dollars: USD, milesFlown: DistanceMiles) -> AwardMiles,
    val medallionQualificationMiles: (dollars: USD, milesFlown: DistanceMiles) -> MedallionQualificationMiles,
    val medallionQualificationSegments: (dollars: USD, milesFlown: DistanceMiles) -> MedallionQualificationSegments,
    val medallionQualificationDollars: (dollars: USD, milesFlown: DistanceMiles) -> MedallionQualificationDollars,
    val displayName: String,
    val medallionMileageBonus: Boolean,
)

data class CalculatedItineraryEarnings(
    val medallionStatus: MedallionStatus,
    val segments: Array<CalculatedSegmentEarning>,
    val soldBy: TicketSeller,
    val baseCost: USD,
    val airlineFees: USD,
    val governmentFeesAndTaxes: USD,
    val creditCard: CreditCard,
) {
    val totalDistance: DistanceMiles = segments.sumOf { it.distance }
    val totalCost: USD = baseCost + airlineFees + governmentFeesAndTaxes
    val totalBaseMiles: AwardMiles = segments.sumOf { it.baseMiles }
    val totalFareClassBonusMiles: AwardMiles = segments.sumOf { it.fareClassBonusMiles }
    val totalMedallionStatusBonusMiles: AwardMiles = segments.sumOf { it.medallionStatusBonusMiles }
    val totalCreditCardBonusMiles: AwardMiles =
        (creditCard.earningRules[soldBy] ?: creditCard.defaultEarningRules)(totalCost)
    val totalMiles: AwardMiles =
        totalBaseMiles + totalFareClassBonusMiles + totalMedallionStatusBonusMiles + totalCreditCardBonusMiles
    val totalMedallionQualificationMiles: MedallionQualificationMiles =
        segments.sumOf { it.medallionQualificationMiles }
    val totalMedallionQualificationSegments: MedallionQualificationSegments =
        segments.sumOf { it.medallionQualificationSegments }
    val totalMedallionQualificationDollars: MedallionQualificationDollars =
        segments.sumOf { it.medallionQualificationDollars }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CalculatedItineraryEarnings) return false

        if (medallionStatus != other.medallionStatus) return false
        if (!segments.contentEquals(other.segments)) return false
        if (soldBy != other.soldBy) return false
        if (baseCost != other.baseCost) return false
        if (airlineFees != other.airlineFees) return false
        if (governmentFeesAndTaxes != other.governmentFeesAndTaxes) return false
        if (creditCard != other.creditCard) return false
        if (totalDistance != other.totalDistance) return false
        if (totalCost != other.totalCost) return false
        if (totalBaseMiles != other.totalBaseMiles) return false
        if (totalFareClassBonusMiles != other.totalFareClassBonusMiles) return false
        if (totalMedallionStatusBonusMiles != other.totalMedallionStatusBonusMiles) return false
        if (totalCreditCardBonusMiles != other.totalCreditCardBonusMiles) return false
        if (totalMiles != other.totalMiles) return false
        if (totalMedallionQualificationMiles != other.totalMedallionQualificationMiles) return false
        if (totalMedallionQualificationSegments != other.totalMedallionQualificationSegments) return false
        if (totalMedallionQualificationDollars != other.totalMedallionQualificationDollars) return false

        return true
    }

    override fun hashCode(): Int {
        var result = medallionStatus.hashCode()
        result = 31 * result + segments.contentHashCode()
        result = 31 * result + soldBy.hashCode()
        result = 31 * result + baseCost.hashCode()
        result = 31 * result + airlineFees.hashCode()
        result = 31 * result + governmentFeesAndTaxes.hashCode()
        result = 31 * result + creditCard.hashCode()
        result = 31 * result + totalDistance.hashCode()
        result = 31 * result + totalCost.hashCode()
        result = 31 * result + totalBaseMiles
        result = 31 * result + totalFareClassBonusMiles
        result = 31 * result + totalMedallionStatusBonusMiles
        result = 31 * result + totalCreditCardBonusMiles
        result = 31 * result + totalMiles
        result = 31 * result + totalMedallionQualificationMiles
        result = 31 * result + totalMedallionQualificationSegments
        result = 31 * result + totalMedallionQualificationDollars
        return result
    }
}

data class CalculatedSegmentEarning(
    val origin: AirportCode?,
    val destination: AirportCode?,
    val fareClass: FareClass?,
    val marketedBy: Airline?,
    var marketedFlightNumber: Int?,
    val ticketedBy: Airline?,
    val operatedBy: Airline?,
    val segmentEarningEligibleCost: USD,
    val segmentTotalCost: USD,
    val distance: DistanceMiles,
    val baseMiles: AwardMiles,
    val fareClassBonusMiles: AwardMiles,
    val medallionStatusBonusMiles: AwardMiles,
    val creditCardBonusMiles: AwardMiles,
    val medallionQualificationMiles: MedallionQualificationMiles,
    val medallionQualificationSegments: MedallionQualificationSegments,
    val medallionQualificationDollars: MedallionQualificationDollars
) {
    val totalMiles: AwardMiles = baseMiles + fareClassBonusMiles + medallionStatusBonusMiles + creditCardBonusMiles
}
