// Note: This filename must be sorted alphabetically after CalculationModel.kt, otherwise the
// values used to set fare rules (eg, _100pctMiles) will not be available yet and will render
// into the resulting JS as null

val earningAirlineOverride: Map<Airline, (segment: ItinerarySegment) -> Airline?> = mapOf(
    Airline.AM to { segment ->
        // AeroMexico-marketed segments count as Delta for earning when tickets are
        // issued on Delta stock (ticket number starting with "006")
        if (segment.ticketedBy in setOf(Airline.DL, Airline.DLX)) {
            segment.ticketedBy
        } else {
            segment.marketedBy
        }
    }
)

enum class Airline(
    selectionCode: String? = null,
    displayCode: String? = null,
    val displayName: String,
    val minimumMedallionQualificationMiles: Int,
    val marketedByEarnPredicate: (segment: ItinerarySegment) -> Boolean = { true },
    val operatedByEarnPredicate: (segment: ItinerarySegment) -> Boolean = { true },
    val fareRules: Map<FareClass, FlightEarningRules>
) {
    //////////////////////////////////
    // Delta                        //
    //////////////////////////////////
    DL(
        displayName = "Delta Air Lines",
        // From: https://www.delta.com/us/en/skymiles/how-to-earn-miles/earn-with-delta#earnMQMs
        minimumMedallionQualificationMiles = 500,
        fareRules = mapOf(
            //@formatter:off
            // ------------------------------| BASE       |  FARE BONUS |  MQM        |  MQS     |  MQD        |
            FareClass.F to FlightEarningRules(_500pctUSDs_, _000earnings, _200pctMiles, _1segment, _100pctUSDs_, "Full Fare Delta One, First and Delta Premium Select", true),
            FareClass.J to FlightEarningRules(_500pctUSDs_, _000earnings, _200pctMiles, _1segment, _100pctUSDs_, "Full Fare Delta One, First and Delta Premium Select", true),

            FareClass.P to FlightEarningRules(_500pctUSDs_, _000earnings, _150pctMiles, _1segment, _100pctUSDs_, "Discounted Delta One, First and Delta Premium Select", true),
            FareClass.A to FlightEarningRules(_500pctUSDs_, _000earnings, _150pctMiles, _1segment, _100pctUSDs_, "Discounted Delta One, First and Delta Premium Select", true),
            FareClass.G to FlightEarningRules(_500pctUSDs_, _000earnings, _150pctMiles, _1segment, _100pctUSDs_, "Discounted Delta One, First and Delta Premium Select", true),
            FareClass.C to FlightEarningRules(_500pctUSDs_, _000earnings, _150pctMiles, _1segment, _100pctUSDs_, "Discounted Delta One, First and Delta Premium Select", true),
            FareClass.D to FlightEarningRules(_500pctUSDs_, _000earnings, _150pctMiles, _1segment, _100pctUSDs_, "Discounted Delta One, First and Delta Premium Select", true),
            FareClass.I to FlightEarningRules(_500pctUSDs_, _000earnings, _150pctMiles, _1segment, _100pctUSDs_, "Discounted Delta One, First and Delta Premium Select", true),
            FareClass.Z to FlightEarningRules(_500pctUSDs_, _000earnings, _150pctMiles, _1segment, _100pctUSDs_, "Discounted Delta One, First and Delta Premium Select", true),

            FareClass.O_AWARD to FlightEarningRules(_000earnings, _000earnings, _150pctMiles, _1segment, _100pctUSDs_, "Award Travel Delta One, First and Delta Premium Select", true),
            FareClass.R_AWARD to FlightEarningRules(_000earnings, _000earnings, _150pctMiles, _1segment, _100pctUSDs_, "Award Travel Delta One, First and Delta Premium Select", true),

            FareClass.Y to FlightEarningRules(_500pctUSDs_, _000earnings, _150pctMiles, _1segment, _100pctUSDs_, "Full Fare Main Cabin", true),
            FareClass.B to FlightEarningRules(_500pctUSDs_, _000earnings, _150pctMiles, _1segment, _100pctUSDs_, "Full Fare Main Cabin", true),

            FareClass.W to FlightEarningRules(_500pctUSDs_, _000earnings, _100pctMiles, _1segment, _100pctUSDs_, "Delta Comfort+", true),
            FareClass.S to FlightEarningRules(_500pctUSDs_, _000earnings, _100pctMiles, _1segment, _100pctUSDs_, "Delta Comfort+", true),

            FareClass.SN_AWARD to FlightEarningRules(_000earnings, _000earnings, _100pctMiles, _1segment, _100pctUSDs_, "Award Travel Delta Comfort+ and Main Cabin", true),
            FareClass.N_AWARD to FlightEarningRules(_000earnings, _000earnings, _100pctMiles, _1segment, _100pctUSDs_, "Award Travel Delta Comfort+ and Main Cabin", true),

            FareClass.M to FlightEarningRules(_500pctUSDs_, _000earnings, _100pctMiles, _1segment, _100pctUSDs_, "Main Cabin, Discounted and Deeply Discounted Main Cabin", true),
            // S is listed twice on Delta's website...
            //FareClass.S to FlightEarningRules(_500pctUSDs_, _000earnings, _100pctMiles, _1segment, _100pctUSDs_, "Main Cabin, Discounted and Deeply Discounted Main Cabin", true),
            FareClass.H to FlightEarningRules(_500pctUSDs_, _000earnings, _100pctMiles, _1segment, _100pctUSDs_, "Main Cabin, Discounted and Deeply Discounted Main Cabin", true),
            FareClass.Q to FlightEarningRules(_500pctUSDs_, _000earnings, _100pctMiles, _1segment, _100pctUSDs_, "Main Cabin, Discounted and Deeply Discounted Main Cabin", true),
            FareClass.K to FlightEarningRules(_500pctUSDs_, _000earnings, _100pctMiles, _1segment, _100pctUSDs_, "Main Cabin, Discounted and Deeply Discounted Main Cabin", true),
            FareClass.L to FlightEarningRules(_500pctUSDs_, _000earnings, _100pctMiles, _1segment, _100pctUSDs_, "Main Cabin, Discounted and Deeply Discounted Main Cabin", true),
            FareClass.U to FlightEarningRules(_500pctUSDs_, _000earnings, _100pctMiles, _1segment, _100pctUSDs_, "Main Cabin, Discounted and Deeply Discounted Main Cabin", true),
            FareClass.T to FlightEarningRules(_500pctUSDs_, _000earnings, _100pctMiles, _1segment, _100pctUSDs_, "Main Cabin, Discounted and Deeply Discounted Main Cabin", true),
            FareClass.X to FlightEarningRules(_500pctUSDs_, _000earnings, _100pctMiles, _1segment, _100pctUSDs_, "Main Cabin, Discounted and Deeply Discounted Main Cabin", true),
            FareClass.V to FlightEarningRules(_500pctUSDs_, _000earnings, _100pctMiles, _1segment, _100pctUSDs_, "Main Cabin, Discounted and Deeply Discounted Main Cabin", true),

            FareClass.R to FlightEarningRules(_000earnings, _000earnings, _000earnings, _0segment, _000earnings, "Basic Economy", false),
            FareClass.SN to FlightEarningRules(_000earnings, _000earnings, _000earnings, _0segment, _000earnings, "Basic Economy", false),
            FareClass.O to FlightEarningRules(_000earnings, _000earnings, _000earnings, _0segment, _000earnings, "Basic Economy", false),
            FareClass.NE to FlightEarningRules(_000earnings, _000earnings, _000earnings, _0segment, _000earnings, "Basic Economy", false),
            //@formatter:on
        )
    ),

    DLX(
        selectionCode = "(*)DL",
        displayCode = "DL",
        displayName = "Delta Air Lines (Exception Fare)",
        // From: https://www.delta.com/us/en/skymiles/how-to-earn-miles/exception-fares
        minimumMedallionQualificationMiles = 500,
        fareRules = mapOf(
            //@formatter:off
            // ------------------------------| BASE       |  FARE BONUS |  MQM        |  MQS     |  MQD        |
            FareClass.J to FlightEarningRules(_100pctMiles, _050pctMiles, _200pctMiles, _1segment, _030pctMiles, "(Exception Fare) Delta One/First Class/Delta Premium Select", true),
            FareClass.F to FlightEarningRules(_100pctMiles, _050pctMiles, _200pctMiles, _1segment, _030pctMiles, "(Exception Fare) Delta One/First Class/Delta Premium Select", true),

            FareClass.C to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "(Exception Fare) Delta One/First Class/Delta Premium Select", true),
            FareClass.D to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "(Exception Fare) Delta One/First Class/Delta Premium Select", true),
            FareClass.I to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "(Exception Fare) Delta One/First Class/Delta Premium Select", true),
            FareClass.Z to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "(Exception Fare) Delta One/First Class/Delta Premium Select", true),
            FareClass.P to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "(Exception Fare) Delta One/First Class/Delta Premium Select", true),
            FareClass.A to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "(Exception Fare) Delta One/First Class/Delta Premium Select", true),
            FareClass.G to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "(Exception Fare) Delta One/First Class/Delta Premium Select", true),

            FareClass.Y to FlightEarningRules(_100pctMiles, _000earnings, _150pctMiles, _1segment, _020pctMiles, "(Exception Fare) Full Fare Main Cabin/Delta Comfort+", true),
            FareClass.B to FlightEarningRules(_100pctMiles, _000earnings, _150pctMiles, _1segment, _020pctMiles, "(Exception Fare) Full Fare Main Cabin/Delta Comfort+", true),

            FareClass.M to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "(Exception Fare) Full Fare Main Cabin/Delta Comfort+", true),
            FareClass.W to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "(Exception Fare) Full Fare Main Cabin/Delta Comfort+", true),
            FareClass.S to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "(Exception Fare) Full Fare Main Cabin/Delta Comfort+", true),

            FareClass.H to FlightEarningRules(_075pctMiles, _000earnings, _100pctMiles, _1segment, _015pctMiles, "(Exception Fare) Main Cabin and Discounted Main Cabin", true),
            FareClass.Q to FlightEarningRules(_075pctMiles, _000earnings, _100pctMiles, _1segment, _015pctMiles, "(Exception Fare) Main Cabin and Discounted Main Cabin", true),
            FareClass.K to FlightEarningRules(_075pctMiles, _000earnings, _100pctMiles, _1segment, _015pctMiles, "(Exception Fare) Main Cabin and Discounted Main Cabin", true),
            FareClass.L to FlightEarningRules(_075pctMiles, _000earnings, _100pctMiles, _1segment, _015pctMiles, "(Exception Fare) Main Cabin and Discounted Main Cabin", true),

            FareClass.U to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "(Exception Fare) Deeply Discounted Main Cabin", true),
            FareClass.T to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "(Exception Fare) Deeply Discounted Main Cabin", true),
            FareClass.X to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "(Exception Fare) Deeply Discounted Main Cabin", true),
            FareClass.V to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "(Exception Fare) Deeply Discounted Main Cabin", true),
            //@formatter:on
        )
    ),

    //////////////////////////////////
    // Core Global Airline Partners //
    //////////////////////////////////
    AF_EU(
        selectionCode = "(eu)AF",
        displayCode = "AF",
        displayName = "Air France (Intra-Europe / Domestic France)",
        minimumMedallionQualificationMiles = 500,
        fareRules = mapOf(
            //@formatter:off
            // Intra-Europe/Domestic France Flights
            // ------------------------------| BASE       |  FARE BONUS |  MQM        |  MQS     |  MQD        |
            FareClass.J to FlightEarningRules(_100pctMiles, _100pctMiles, _200pctMiles, _1segment, _040pctMiles, "Business", true),

            FareClass.C to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.D to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.I to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.Z to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),

            FareClass.Y to FlightEarningRules(_100pctMiles, _025pctMiles, _150pctMiles, _1segment, _025pctMiles, "Full Economy", true),
            FareClass.B to FlightEarningRules(_100pctMiles, _025pctMiles, _150pctMiles, _1segment, _025pctMiles, "Full Economy", true),
            FareClass.M to FlightEarningRules(_100pctMiles, _025pctMiles, _150pctMiles, _1segment, _025pctMiles, "Full Economy", true),

            FareClass.W to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.P to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.F to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.U to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.K to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),

            FareClass.S to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.A to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.H to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.L to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.Q to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.T to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.E to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.N to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),

            FareClass.R to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _050pctMiles, "Deep Discount Economy", true),
            FareClass.G to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _050pctMiles, "Deep Discount Economy", true),
            FareClass.V to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _050pctMiles, "Deep Discount Economy", true),
            //@formatter:on
        )
    ),

    AF_INTL(
        selectionCode = "(intl)AF",
        displayCode = "AF",
        displayName = "Air France (All Other Int'l Flights)",
        minimumMedallionQualificationMiles = 500,
        fareRules = mapOf(
            //@formatter:off
            // International Flights (Excluding Intra-Europe Flights)
            // ------------------------------| BASE       |  FARE BONUS |  MQM        |  MQS     |  MQD        |
            FareClass.P to FlightEarningRules(_100pctMiles, _200pctMiles, _200pctMiles, _1segment, _060pctMiles, "First", true),
            FareClass.F to FlightEarningRules(_100pctMiles, _200pctMiles, _200pctMiles, _1segment, _060pctMiles, "First", true),

            FareClass.J to FlightEarningRules(_100pctMiles, _100pctMiles, _200pctMiles, _1segment, _040pctMiles, "Business", true),

            FareClass.C to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.D to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.I to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.Z to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),

            FareClass.O to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Business", true),

            FareClass.W to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "Premium Economy", true),
            FareClass.S to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "Premium Economy", true),
            FareClass.A to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "Premium Economy", true),

            FareClass.Y to FlightEarningRules(_100pctMiles, _025pctMiles, _150pctMiles, _1segment, _025pctMiles, "Full Economy", true),
            FareClass.B to FlightEarningRules(_100pctMiles, _025pctMiles, _150pctMiles, _1segment, _025pctMiles, "Full Economy", true),
            FareClass.M to FlightEarningRules(_100pctMiles, _025pctMiles, _150pctMiles, _1segment, _025pctMiles, "Full Economy", true),

            FareClass.U to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.K to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),

            FareClass.H to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.L to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.Q to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.T to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.E to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.N to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),

            FareClass.R to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", true),
            FareClass.G to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", true),
            FareClass.V to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", true),
            FareClass.X to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", true),
            //@formatter:on
        )
    ),

    AM(
        displayName = "AeroMexico",
        // From https://www.delta.com/us/en/skymiles/how-to-earn-miles/airline-partners
        minimumMedallionQualificationMiles = 500,
        fareRules = mapOf(
            //@formatter:off
            // On/After April 1, 2018
            // ------------------------------| BASE       |  FARE BONUS |  MQM        |  MQS     |  MQD        |
            FareClass.J to FlightEarningRules(_100pctMiles, _100pctMiles, _200pctMiles, _1segment, _040pctMiles, "Business", true),

            FareClass.C to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.D to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.I to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),

            FareClass.W to FlightEarningRules(_100pctMiles, _025pctMiles, _150pctMiles, _1segment, _025pctMiles, "Full Economy", true),
            FareClass.Y to FlightEarningRules(_100pctMiles, _025pctMiles, _150pctMiles, _1segment, _025pctMiles, "Full Economy", true),

            FareClass.B to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.M to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.K to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.H to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.Q to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.L to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.T to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.U to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),

            FareClass.E to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.N to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.R to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),

            FareClass.V to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", true)
            //@formatter:on
        )
    ),

    KE(
        displayName = "Korean Air",
        // From https://www.delta.com/us/en/skymiles/how-to-earn-miles/airline-partners
        minimumMedallionQualificationMiles = 500,
        marketedByEarnPredicate = { segment -> segment.marketedFlightNumber !in 9000..9999 },
        fareRules = mapOf(
            //@formatter:off
            // On/After May 1, 2018
            // ------------------------------| BASE       |  FARE BONUS |  MQM        |  MQS     |  MQD        |
            FareClass.P to FlightEarningRules(_100pctMiles, _200pctMiles, _200pctMiles, _1segment, _060pctMiles, "First (International)", true),
            FareClass.F to FlightEarningRules(_100pctMiles, _200pctMiles, _200pctMiles, _1segment, _060pctMiles, "First (International)", true),

            FareClass.J to FlightEarningRules(_100pctMiles, _100pctMiles, _200pctMiles, _1segment, _040pctMiles, "Business", true),

            FareClass.C to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.D to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.I to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.R to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),

            FareClass.W to FlightEarningRules(_100pctMiles, _025pctMiles, _125pctMiles, _1segment, _025pctMiles, "Full Economy", true),
            FareClass.Y to FlightEarningRules(_100pctMiles, _025pctMiles, _125pctMiles, _1segment, _025pctMiles, "Full Economy", true),
            FareClass.B to FlightEarningRules(_100pctMiles, _025pctMiles, _125pctMiles, _1segment, _025pctMiles, "Full Economy", true),

            FareClass.S to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy (International)", true),

            FareClass.M to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.H to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.E to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),

            FareClass.G to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.Q to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),

            FareClass.K to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Discounted Economy (International)", true),
            FareClass.L to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Discounted Economy (International)", true),
            FareClass.U to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Discounted Economy (International)", true),

            FareClass.T to FlightEarningRules(_025pctMiles, _000earnings, _025pctMiles, _1segment, _005pctMiles, "Deep Discounted Economy", true),

            FareClass.N to FlightEarningRules(_025pctMiles, _000earnings, _025pctMiles, _1segment, _005pctMiles, "Deep Discounted Economy (International)", true),
            //@formatter:on
        )
    ),

    KL(
        displayName = "KLM",
        // From https://www.delta.com/us/en/skymiles/how-to-earn-miles/airline-partners
        minimumMedallionQualificationMiles = 500,
        marketedByEarnPredicate = { segment ->
            segment.marketedFlightNumber !in 100..199
                && segment.marketedFlightNumber !in 211..399
        },
        fareRules = mapOf(
            //@formatter:off
            // On/After May 1, 2018
            // ------------------------------| BASE       |  FARE BONUS |  MQM        |  MQS     |  MQD        |
            FareClass.J to FlightEarningRules(_100pctMiles, _100pctMiles, _200pctMiles, _1segment, _040pctMiles, "Business", true),

            FareClass.C to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.D to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.I to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.Z to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),

            FareClass.O to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Business", true),

            FareClass.Y to FlightEarningRules(_100pctMiles, _025pctMiles, _150pctMiles, _1segment, _025pctMiles, "Full Economy", true),
            FareClass.B to FlightEarningRules(_100pctMiles, _025pctMiles, _150pctMiles, _1segment, _025pctMiles, "Full Economy", true),
            FareClass.M to FlightEarningRules(_100pctMiles, _025pctMiles, _150pctMiles, _1segment, _025pctMiles, "Full Economy", true),

            FareClass.U to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.K to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.W to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.P to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.F to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),

            FareClass.H to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.L to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.Q to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.T to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.E to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.N to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.S to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.A to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),

            FareClass.R to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", true),
            FareClass.G to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", true),
            FareClass.V to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", true),
            FareClass.X to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", true),
            //@formatter:on
        )
    ),

    LA(
        displayName = "LATAM Airlines",
        // From https://www.delta.com/us/en/skymiles/how-to-earn-miles/airline-partners
        minimumMedallionQualificationMiles = 500,
        marketedByEarnPredicate = { segment ->
            segment.operatedBy in setOf(DL, DLX, LA)
        },
        fareRules = mapOf(
            //@formatter:off
            // On/After April 1, 2020
            // ------------------------------| BASE       |  FARE BONUS |  MQM        |  MQS     |  MQD        |
            FareClass.J to FlightEarningRules(_100pctMiles, _100pctMiles, _200pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.C to FlightEarningRules(_100pctMiles, _100pctMiles, _200pctMiles, _1segment, _040pctMiles, "Business", true),

            FareClass.D to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "Business", true),
            FareClass.I to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "Business", true),
            FareClass.Z to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "Business", true),

            FareClass.W to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "Premium Economy", true),

            FareClass.P to FlightEarningRules(_100pctMiles, _025pctMiles, _150pctMiles, _1segment, _025pctMiles, "Premium Economy", true),

            FareClass.Y to FlightEarningRules(_100pctMiles, _025pctMiles, _150pctMiles, _1segment, _025pctMiles, "Full Economy", true),
            FareClass.B to FlightEarningRules(_100pctMiles, _025pctMiles, _150pctMiles, _1segment, _025pctMiles, "Full Economy", true),

            FareClass.H to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.K to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.M to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),

            FareClass.L to FlightEarningRules(_075pctMiles, _000earnings, _100pctMiles, _1segment, _015pctMiles, "Economy", true),
            FareClass.V to FlightEarningRules(_075pctMiles, _000earnings, _100pctMiles, _1segment, _015pctMiles, "Economy", true),
            FareClass.X to FlightEarningRules(_075pctMiles, _000earnings, _100pctMiles, _1segment, _015pctMiles, "Economy", true),

            FareClass.S to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.N to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.Q to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),

            FareClass.O to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Deeply Discounted Economy", true),
            FareClass.G to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Deeply Discounted Economy", true),
            FareClass.A to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Deeply Discounted Economy", true),
            //@formatter:on
        )
    ),

    MU(
        displayName = "China Eastern",
        // From https://www.delta.com/us/en/skymiles/how-to-earn-miles/airline-partners
        minimumMedallionQualificationMiles = 500,
        fareRules = mapOf(
            //@formatter:off
            // On/After January 1, 2018
            // ------------------------------| BASE       |  FARE BONUS |  MQM        |  MQS     |  MQD        |
            FareClass.U to FlightEarningRules(_100pctMiles, _200pctMiles, _200pctMiles, _1segment, _060pctMiles, "First", true),

            FareClass.F to FlightEarningRules(_100pctMiles, _200pctMiles, _150pctMiles, _1segment, _060pctMiles, "First", true),

            FareClass.P_INT to FlightEarningRules(_100pctMiles, _200pctMiles, _200pctMiles, _1segment, _060pctMiles, "First, International", true),
            FareClass.P_DOM to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "First, China Domestic", true),

            FareClass.J to FlightEarningRules(_100pctMiles, _100pctMiles, _200pctMiles, _1segment, _040pctMiles, "Business", true),

            FareClass.C to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.D to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.Q to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.I to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),

            FareClass.W to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "Premium Economy", true),

            FareClass.Y to FlightEarningRules(_100pctMiles, _025pctMiles, _150pctMiles, _1segment, _025pctMiles, "Full Economy", true),

            FareClass.B to FlightEarningRules(_100pctMiles, _000earnings, _150pctMiles, _1segment, _020pctMiles, "Economy", true),

            FareClass.M to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.E to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),
            FareClass.H to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy", true),

            FareClass.K to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.L to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.N to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.R to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.S to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),
            FareClass.V to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Discounted Economy", true),

            FareClass.T to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", true),
            FareClass.G to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", true),
            FareClass.Z to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", true),
            //@formatter:on
        )
    ),

    VS(
        displayName = "Virgin Atlantic",
        // From https://www.delta.com/us/en/skymiles/how-to-earn-miles/airline-partners
        minimumMedallionQualificationMiles = 250,
        marketedByEarnPredicate = { segment ->
            segment.operatedBy in setOf(DL, DLX, VS)
        },
        fareRules = mapOf(
            //@formatter:off
            // On/After April 10, 2018
            // ------------------------------| BASE       |  FARE BONUS |  MQM        |  MQS     |  MQD        |
            FareClass.J to FlightEarningRules(_100pctMiles, _100pctMiles, _200pctMiles, _1segment, _040pctMiles, "Business", true),

            FareClass.C to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.D to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.I to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),
            FareClass.Z to FlightEarningRules(_100pctMiles, _100pctMiles, _150pctMiles, _1segment, _040pctMiles, "Business", true),

            FareClass.W to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "Premium Economy", true),
            FareClass.S to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "Premium Economy", true),
            FareClass.K to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "Premium Economy", true),
            FareClass.H to FlightEarningRules(_100pctMiles, _050pctMiles, _150pctMiles, _1segment, _030pctMiles, "Premium Economy", true),

            FareClass.V to FlightEarningRules(_100pctMiles, _025pctMiles, _125pctMiles, _1segment, _025pctMiles, "Economy (Delight, Classic or Light)", true),

            FareClass.Y to FlightEarningRules(_100pctMiles, _025pctMiles, _125pctMiles, _1segment, _025pctMiles, "Economy (Delight, Classic or Light)", true),
            FareClass.B to FlightEarningRules(_100pctMiles, _025pctMiles, _125pctMiles, _1segment, _025pctMiles, "Economy (Delight, Classic or Light)", true),

            FareClass.R to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy (Delight, Classic or Light)", true),
            FareClass.L to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy (Delight, Classic or Light)", true),
            FareClass.U to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Economy (Delight, Classic or Light)", true),

            FareClass.M to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Economy (Delight, Classic or Light)", true),
            FareClass.E to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Economy (Delight, Classic or Light)", true),
            FareClass.Q to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Economy (Delight, Classic or Light)", true),
            FareClass.X to FlightEarningRules(_050pctMiles, _000earnings, _100pctMiles, _1segment, _010pctMiles, "Economy (Delight, Classic or Light)", true),

            FareClass.N to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Economy (Delight, Classic or Light)", true),
            FareClass.O to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Economy (Delight, Classic or Light)", true),

            FareClass.T to FlightEarningRules(_025pctMiles, _000earnings, _100pctMiles, _1segment, _005pctMiles, "Economy (Delight, Classic or Light)", true),
            //@formatter:on
        )
    ),

    //////////////////////////////////
    // Global Airline Partners      //
    //////////////////////////////////
    CZ(
        displayName = "China Southern",
        // From https://www.delta.com/us/en/skymiles/how-to-earn-miles/airline-partners
        minimumMedallionQualificationMiles = 250,
        // Note: Beginning on flights on or after January 1, 2020, SkyMiles Members will no longer earn miles on
        // China Southern (CZ) flights that are marketed or operated by SkyTeam partner airlines. SkyMiles
        // Members flying with China Southern will continue earning miles only on the following flights:
        //   * China Southern-marketed/China Southern-operated
        //   * Delta-marketed/China Southern-operated
        //   * China Southern-marketed/Delta-operated
        marketedByEarnPredicate = { segment ->
            segment.operatedBy in setOf(DL, DLX, CZ)
        },
        operatedByEarnPredicate = { segment ->
            segment.marketedBy in setOf(DL, DLX, CZ)
        },
        fareRules = mapOf(
            //@formatter:off
            // On/After January 1, 2018
            // ------------------------------| BASE       |  FARE BONUS |  MQM        |  MQS     |  MQD        |
            FareClass.F to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "First", false),

            FareClass.J to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Business", false),
            FareClass.C to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Business", false),
            FareClass.D to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Business", false),
            FareClass.I to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Business", false),

            FareClass.W to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Premium Economy", false),
            FareClass.S to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Premium Economy", false),

            FareClass.Y to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Full Economy", false),

            FareClass.P to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Economy", false),
            FareClass.B to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Economy", false),
            FareClass.M to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Economy", false),
            FareClass.H to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Economy", false),
            FareClass.K to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Economy", false),

            FareClass.U to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Discounted Economy", false),
            FareClass.A to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Discounted Economy", false),
            FareClass.L to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Discounted Economy", false),
            FareClass.E to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Discounted Economy", false),
            FareClass.Q to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Discounted Economy", false),

            FareClass.V to FlightEarningRules(_025pctMiles, _000earnings, _025pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", false),
            FareClass.Z to FlightEarningRules(_025pctMiles, _000earnings, _025pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", false),
            FareClass.T to FlightEarningRules(_025pctMiles, _000earnings, _025pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", false),
            FareClass.N to FlightEarningRules(_025pctMiles, _000earnings, _025pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", false),
            FareClass.R to FlightEarningRules(_025pctMiles, _000earnings, _025pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", false),
            //@formatter:on
        )
    ),
    VN(
        displayName = "Vietnam Airlines",
        // From https://www.delta.com/us/en/skymiles/how-to-earn-miles/airline-partners
        minimumMedallionQualificationMiles = 250,
        marketedByEarnPredicate = { segment ->
            segment.marketedFlightNumber !in 8050..8063
                && segment.marketedFlightNumber !in 8070..8072
                && segment.marketedFlightNumber !in 8430..8433
                && segment.marketedFlightNumber !in 8440..8443
        },
        fareRules = mapOf(
            //@formatter:off
            // On/After January 1, 2020
            // ------------------------------| BASE       |  FARE BONUS |  MQM        |  MQS     |  MQD        |
            FareClass.J to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Business", false),
            FareClass.C to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Business", false),
            FareClass.D to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Business", false),
            // Why the asterisk? Not quite sure...
            FareClass.I to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Business (*)", false),

            FareClass.W to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Premium Economy", false),
            FareClass.Z to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Premium Economy", false),
            // Why the asterisk? Not quite sure...
            FareClass.U to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Premium Economy (*)", false),

            FareClass.Y to FlightEarningRules(_100pctMiles, _000earnings, _100pctMiles, _1segment, _020pctMiles, "Full Economy", false),

            FareClass.M to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Economy", false),
            FareClass.B to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Economy", false),
            FareClass.S to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Economy", false),
            FareClass.H to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Economy", false),
            FareClass.K to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Economy", false),
            FareClass.L to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Economy", false),
            FareClass.Q to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Economy", false),
            FareClass.N to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Economy", false),
            FareClass.R to FlightEarningRules(_050pctMiles, _000earnings, _050pctMiles, _1segment, _010pctMiles, "Economy", false),

            FareClass.T to FlightEarningRules(_025pctMiles, _000earnings, _025pctMiles, _1segment, _005pctMiles, "Discounted Economy", false),
            // Why the asterisk? Not quite sure...
            FareClass.E to FlightEarningRules(_025pctMiles, _000earnings, _025pctMiles, _1segment, _005pctMiles, "Discounted Economy (*)", false),
            FareClass.A to FlightEarningRules(_025pctMiles, _000earnings, _025pctMiles, _1segment, _005pctMiles, "Discounted Economy", false),

            FareClass.G to FlightEarningRules(_010pctMiles, _000earnings, _010pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", false),
            FareClass.P to FlightEarningRules(_010pctMiles, _000earnings, _010pctMiles, _1segment, _005pctMiles, "Deep Discount Economy", false),
            //@formatter:on
        )
    ),

    // TODO: Add more SkyTeam airlines

    ;

    val selectionCode: String
    val displayCode: String

    init {
        this.selectionCode = selectionCode ?: this.name
        this.displayCode = displayCode ?: this.name
    }
}
