@file:Suppress("unused")

enum class FareClass {
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z,
    NE, SN,
    P_INT, P_DOM,
    N_AWARD, O_AWARD, R_AWARD, SN_AWARD,
}

data class Itinerary(
    var medallionStatus: MedallionStatus = MedallionStatus.NONE,
    var segments: Array<ItinerarySegment> = emptyArray(),
    var soldBy: TicketSeller = TicketSeller.DL,
    var baseCost: USD = 0.0,
    var airlineFees: USD? = null,
    var governmentFeesAndTaxes: USD? = null,
    var creditCard: CreditCard = CreditCard.OTHER,
) {
    companion object {
        private val serializedCodeRegex: Regex = Regex(
            "^" +
                "(?:#(?:results/)?)?" +
                "(?<medallionStatus>[${MedallionStatus.values().map(MedallionStatus::shortCode).joinToString("")}])" +
                "(?<soldBy>${TicketSeller.values().map(TicketSeller::name).joinToString("|")})" +
                "(?<baseCost>\\d+(?:\\.\\d{1,2})?)" +
                "/" +
                "(?<airlineFees>\\d+(?:\\.\\d{1,2})?|_)" +
                "/" +
                "(?<governmentTaxesAndFees>\\d+(?:\\.\\d{1,2})?|_)" +
                "(?<creditCard>${CreditCard.values().map(CreditCard::name).joinToString("|")})" +
                "/" +
                "(?<segments>.*)"
        )

        @JsName("parseFromSerializedCode")
        fun parseFromSerializedCode(serializedCode: String): Itinerary? {
            val match = serializedCodeRegex.find(serializedCode)
                ?: return null

            return Itinerary(
                medallionStatus = MedallionStatus.valueFromShortCode(match.groups["medallionStatus"]!!.value[0]),
                soldBy = TicketSeller.valueOf(match.groups["soldBy"]!!.value),
                baseCost = match.groups["baseCost"]!!.value.toDouble(),
                airlineFees = match.groups["airlineFees"]!!.value.let(String::toDoubleOrNull),
                governmentFeesAndTaxes = match.groups["governmentTaxesAndFees"]!!.value.let(String::toDoubleOrNull),
                creditCard = CreditCard.valueOf(match.groups["creditCard"]!!.value),
                segments = match.groups["segments"]!!.value
                    .split("/")
                    .mapNotNull(ItinerarySegment.Companion::parseFromSerializedCode)
                    .toTypedArray()
            )
        }
    }

    val serializedCode: String
        get() = "${medallionStatus.shortCode}${soldBy.name}${baseCost}/${airlineFees ?: "_"}/${governmentFeesAndTaxes ?: "_"}${creditCard.name}/${
            segments.map { it.serializedCode }.joinToString("/")
        }"

    val totalDistance: DistanceMiles
        get() = segments.sumOf { it.distance }

    val earningEligibleCost: USD
        get() = baseCost + (airlineFees ?: 0.0)

    val totalCost: USD
        get() = earningEligibleCost + (governmentFeesAndTaxes ?: 0.0)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Itinerary) return false

        if (medallionStatus != other.medallionStatus) return false
        if (!segments.contentEquals(other.segments)) return false
        if (soldBy != other.soldBy) return false
        if (baseCost != other.baseCost) return false
        if (airlineFees != other.airlineFees) return false
        if (governmentFeesAndTaxes != other.governmentFeesAndTaxes) return false
        if (creditCard != other.creditCard) return false

        return true
    }

    override fun hashCode(): Int {
        var result = medallionStatus.hashCode()
        result = 31 * result + segments.contentHashCode()
        result = 31 * result + soldBy.hashCode()
        result = 31 * result + baseCost.hashCode()
        result = 31 * result + (airlineFees?.hashCode() ?: 0)
        result = 31 * result + (governmentFeesAndTaxes?.hashCode() ?: 0)
        result = 31 * result + creditCard.hashCode()
        return result
    }
}

data class ItinerarySegment(
    var origin: AirportCode? = null,
    var destination: AirportCode? = null,
    var fareClass: FareClass? = null,
    var marketedBy: Airline? = Airline.DL,
    var marketedFlightNumber: Int? = null,
    var operatedBy: Airline? = marketedBy,
    var ticketedBy: Airline? = operatedBy,
) {
    companion object {

        private val serializedCodeRegex: Regex = Regex(
            "^" +
                "(?<origin>${AirportCode.values().map(AirportCode::name).joinToString("|")}|_)" +
                "(?<destination>${AirportCode.values().map(AirportCode::name).joinToString("|")}|_)" +
                "(?<marketedBy>${Airline.values().map(Airline::name).joinToString("|")}|_)" +
                "(?<marketedFlightNumber>\\d{1,4}|_)" +
                "(?<operatedBy>${Airline.values().map(Airline::name).joinToString("|")}|_)" +
                "(?<ticketedBy>${Airline.values().map(Airline::name).joinToString("|")}|_)" +
                "(?<fareClass>${FareClass.values().map(FareClass::name).joinToString("|")}|_)" +
                "$"
        )

        fun parseFromSerializedCode(code: String): ItinerarySegment? {
            val match = serializedCodeRegex.find(code)
                ?: return null

            //@formatter:off
            return ItinerarySegment(
                origin = try { AirportCode.valueOf(match.groups["origin"]!!.value) } catch (_: Exception) { null },
                destination = try { AirportCode.valueOf(match.groups["destination"]!!.value)} catch (_: Exception) { null },
                marketedBy = try { Airline.valueOf(match.groups["marketedBy"]!!.value)} catch (_: Exception) { null },
                marketedFlightNumber = match.groups["marketedFlightNumber"]!!.value.toIntOrNull(),
                operatedBy = try { Airline.valueOf(match.groups["operatedBy"]!!.value)} catch (_: Exception) { null },
                ticketedBy = try { Airline.valueOf(match.groups["ticketedBy"]!!.value)} catch (_: Exception) { null },
                fareClass = try { FareClass.valueOf(match.groups["fareClass"]!!.value)} catch (_: Exception) { null }
            )
            //@formatter:on

        }
    }

    val distance: DistanceMiles
        get() = segmentDistances[origin to destination] ?: DistanceMiles.NaN

    val serializedCode: String
        get() = "${origin?.name ?: "_"}${destination?.name ?: "_"}${marketedBy?.name ?: "_"}${marketedFlightNumber ?: "_"}${operatedBy?.name ?: "_"}${ticketedBy?.name ?: "_"}${fareClass?.name ?: "_"}"
}
