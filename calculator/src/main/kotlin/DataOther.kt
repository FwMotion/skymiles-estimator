@file:Suppress("unused")

enum class MedallionStatus(
    val shortCode: Char,
    val displayName: String,
    val earnBonus: MedallionBonusEarningRule
) {
    NONE('N', "None", awardBonus000Pct),
    MEMBER('M', "Member", awardBonus000Pct),
    SILVER('S', "Silver Medallion", awardBonus040Pct),
    GOLD('G', "Gold Medallion", awardBonus060Pct),
    PLATINUM('P', "Platinum Medallion", awardBonus080Pct),
    DIAMOND('D', "Diamond Medallion", awardBonus120Pct);

    companion object {
        fun valueFromShortCode(shortCode: Char): MedallionStatus =
            MedallionStatus.values().first { shortCode == it.shortCode }
    }
}

enum class TicketSeller(
    val displayName: String,
) {
    AMEX("American Express Travel"),
    CITI_TY("Citi ThankYou Rewards Travel"),
    USB_R("USBank Altitude Rewards Travel"),
    CHASE_UR("Chase UltimateRewards Travel"),
    AF("Air France"),
    AM("AeroMexico"),
    DL("Delta Air Lines"),
    KE("Korean Air"),
    KL("KLM"),
    LA("LATAM Airlines"),
    MU("China Eastern"),
    VS("Virgin Atlantic"),
}

enum class CreditCard(
    val defaultEarningRules: CreditCardBonusEarningRule,
    val earningRules: Map<TicketSeller, CreditCardBonusEarningRule>,
    val displayName: String,
) {
    // From:
    // https://www.delta.com/us/en/skymiles/airline-credit-cards/card-comparison
    // https://apply.americanexpress.com/delta-april22/

    DL_RESERVE(creditBonus100Pct, mapOf(TicketSeller.DL to creditBonus300Pct), "Delta SkyMiles Reserve Card (AmEx)"),
    DL_PLAT(creditBonus100Pct, mapOf(TicketSeller.DL to creditBonus300Pct), "Delta SkyMiles Platinum Card (AmEx)"),
    DL_GOLD(creditBonus100Pct, mapOf(TicketSeller.DL to creditBonus200Pct), "Delta SkyMiles Gold Card (AmEx)"),
    DL_BLUE(creditBonus100Pct, mapOf(TicketSeller.DL to creditBonus200Pct), "Delta SkyMiles Blue Card (AmEx)"),
    DL_BUS_RY(
        creditBonus150Pct,
        mapOf(TicketSeller.DL to creditBonus300Pct),
        "Delta SkyMiles Reserve Business Card (AmEx) - $150k threshold met"
    ),
    DL_BUS_RN(
        creditBonus100Pct,
        mapOf(TicketSeller.DL to creditBonus300Pct),
        "Delta SkyMiles Reserve Business Card (AmEx) - $150k threshold not met"
    ),
    DL_BUS_P(
        creditBonus150PctWithThreshold(5000.0),
        mapOf(TicketSeller.DL to creditBonus300Pct),
        "Delta SkyMiles Platinum Business Card (AmEx)"
    ),
    DL_BUS_G(
        creditBonus100Pct,
        mapOf(TicketSeller.DL to creditBonus200Pct),
        "Delta SkyMiles Gold Business Card (AmEx)"
    ),

    // From:
    // https://www.americanexpress.com/us/credit-cards/category/travel-rewards/

    AMEX_PLAT(
        creditBonus100Pct,
        mapOf(
            TicketSeller.AMEX to creditBonus500Pct,
            TicketSeller.AF to creditBonus500Pct,
            TicketSeller.AM to creditBonus500Pct,
            TicketSeller.DL to creditBonus500Pct,
            TicketSeller.KE to creditBonus500Pct,
            TicketSeller.KL to creditBonus500Pct,
            TicketSeller.LA to creditBonus500Pct,
            TicketSeller.MU to creditBonus500Pct,
            TicketSeller.VS to creditBonus500Pct,
        ),
        "American Express Platinum Card - points counted 1:1 as miles"
    ),
    AMEX_GOLD(
        creditBonus100Pct,
        mapOf(
            TicketSeller.AMEX to creditBonus300Pct,
            TicketSeller.AF to creditBonus300Pct,
            TicketSeller.AM to creditBonus300Pct,
            TicketSeller.DL to creditBonus300Pct,
            TicketSeller.KE to creditBonus300Pct,
            TicketSeller.KL to creditBonus300Pct,
            TicketSeller.LA to creditBonus300Pct,
            TicketSeller.MU to creditBonus300Pct,
            TicketSeller.VS to creditBonus300Pct,
        ),
        "American Express Gold Card - points counted 1:1 as miles"
    ),
    AMEX_GREEN(
        creditBonus100Pct,
        mapOf(
            TicketSeller.AMEX to creditBonus300Pct,
            TicketSeller.AF to creditBonus300Pct,
            TicketSeller.AM to creditBonus300Pct,
            TicketSeller.DL to creditBonus300Pct,
            TicketSeller.KE to creditBonus300Pct,
            TicketSeller.KL to creditBonus300Pct,
            TicketSeller.LA to creditBonus300Pct,
            TicketSeller.MU to creditBonus300Pct,
            TicketSeller.VS to creditBonus300Pct,
        ),
        "American Express Green Card - points counted 1:1 as miles"
    ),

    // From (travel section):
    // https://www.americanexpress.com/us/credit-cards/card/amex-everyday/
    // https://www.americanexpress.com/us/credit-cards/card/amex-everyday-preferred/
    AMEX_EDP(
        creditBonus100Pct,
        mapOf(
            TicketSeller.AMEX to creditBonus200Pct,
        ),
        "American Express EveryDay Preferred Card - points counted 1:1 as miles"
    ),
    AMEX_ED(
        creditBonus100Pct,
        mapOf(
            TicketSeller.AMEX to creditBonus200Pct,
        ),
        "American Express EveryDay Card - points counted 1:1 as miles"
    ),

    // TODO: More credit cards that give points which are transferrable to Delta SkyMiles

    OTHER(creditBonus000Pct, emptyMap(), "Other")
}

enum class AirportCode(
    val city: String
) {
    AMS("Amsterdam"),
    ANC("Anchorage"),
    ATL("Atlanta"),
    BKK("Bangkok"),
    CDG("Paris"),
    CUN("Cancun"),
    CVG("Cincinnati"),
    DEL("New Delhi"),
    DFW("Dallas/Fort Worth"),
    DTW("Detroit/Wayne County"),
    DXB("Dubai"),
    FAI("Fairbanks"),
    GDL("Guadalajara"),
    HNL("Honolulu"),
    HND("Tokyo"),
    IAH("Houston"),
    ICN("Seoul/Incheon"),
    JFK("New York (JFK)"),
    LAX("Los Angeles"),
    LIH("Lihue"),
    LIM("Lima"),
    MCI("Kansas City"),
    MEX("Mexico City"),
    MSP("Minneapolis/St. Paul"),
    OMA("Omaha"),
    ORD("Chicago"),
    //PVG("Shanghai (Pudong)"),
    PVR("Puerto Vallarta"),
    SDQ("Santo Domingo"),
    SEA("Seattle/Tacoma"),
    SFO("San Francisco"),
    SGN("Ho Chi Minh (Saigon)"),
    SLC("Salt Lake City"),
    TPE("Taipei"),
}

val segmentDistances: Map<Pair<AirportCode, AirportCode>, DistanceMiles> = buildMap {
    val distances = mapOf(
        AirportCode.AMS to AirportCode.ATL to 4401.0,  // calculated from booking flow total MQMs
        AirportCode.AMS to AirportCode.DEL to 3961.0,  // estimated from airmilescalculator.com
        AirportCode.AMS to AirportCode.DTW to 3940.0,  // calculated from booking flow total MQMs
        AirportCode.AMS to AirportCode.DXB to 3215.0,  // estimated from booking flow total MQMs
        AirportCode.AMS to AirportCode.MSP to 4166.0,  // calculated from booking flow total MQMs
        AirportCode.ANC to AirportCode.MSP to 2518.0,  // calculated from booking flow total MQMs
        AirportCode.ANC to AirportCode.SEA to 1448.0,  // calculated from booking flow total MQMs
        AirportCode.ATL to AirportCode.CDG to 4394.0,  // calculated from booking flow total MQMs
        AirportCode.ATL to AirportCode.CUN to 881.0,   // calculated from booking flow total MQMs
        AirportCode.ATL to AirportCode.CVG to 373.0,   // estimated from airmilescalculator.com
        AirportCode.ATL to AirportCode.DFW to 731.0,   // verified
        AirportCode.ATL to AirportCode.DTW to 595.0,   // calculated from booking flow total MQMs
        AirportCode.ATL to AirportCode.HND to 6883.0,  // calculated from booking flow total MQMs
        AirportCode.ATL to AirportCode.HNL to 4502.0,  // calculated from booking flow total MQMs
        AirportCode.ATL to AirportCode.IAH to 689.0,   // calculated from booking flow total MQMs
        AirportCode.ATL to AirportCode.ICN to 7153.0,  // verified
        AirportCode.ATL to AirportCode.JFK to 760.0,   // calculated from booking flow total MQMs
        AirportCode.ATL to AirportCode.MCI to 693.0,   // calculated from booking flow total MQMs
        AirportCode.ATL to AirportCode.MEX to 1557.0,  // calculated from booking flow total MQMs
        AirportCode.ATL to AirportCode.OMA to 821.0,   // verified
        AirportCode.ATL to AirportCode.ORD to 606.0,   // calculated from booking flow total MQMs
        AirportCode.ATL to AirportCode.PVR to 1558.0,  // calculated from booking flow total MQMs
        AirportCode.ATL to AirportCode.SEA to 2181.0,  // calculated from booking flow total MQMs
        AirportCode.ATL to AirportCode.SFO to 2139.0,  // calculated from booking flow total MQMs
        AirportCode.BKK to AirportCode.ICN to 2270.0,  // calculated from booking flow total MQMs
        AirportCode.CDG to AirportCode.DEL to 4087.0,  // estimated from airmilescalculator.com
        AirportCode.CDG to AirportCode.DTW to 3962.0,  // calculated from booking flow total MQMs
        AirportCode.CDG to AirportCode.DXB to 3259.0,  // estimated from booking flow total MQMs
        AirportCode.CDG to AirportCode.HND to 6046.0,  // estimated from airmilescalculator.com
        AirportCode.CDG to AirportCode.IAH to 5026.0,  // calculated from booking flow total MQMs
        AirportCode.CDG to AirportCode.MSP to 4222.0,  // calculated from booking flow total MQMs
        AirportCode.CDG to AirportCode.ORD to 4152.0,  // estimated from airmilescalculator.com
        AirportCode.CDG to AirportCode.SEA to 5016.0,  // calculated from booking flow total MQMs
        AirportCode.CUN to AirportCode.LAX to 2119.0,  // calculated from booking flow total MQMs
        AirportCode.CUN to AirportCode.SLC to 2005.0,  // calculated from booking flow total MQMs
        AirportCode.CVG to AirportCode.JFK to 589.0,   // calculated from booking flow total MQMs
        AirportCode.DFW to AirportCode.ICN to 6842.0,  // calculated from booking flow total MQMs
        AirportCode.DFW to AirportCode.MSP to 852.0,   // calculated from booking flow total MQMs
        AirportCode.DTW to AirportCode.ICN to 6636.0,  // calculated from booking flow total MQMs
        AirportCode.DTW to AirportCode.JFK to 509.0,   // calculated from booking flow total MQMs
        AirportCode.DTW to AirportCode.OMA to 651.0,   // calculated from booking flow total MQMs
        //AirportCode.FAI to AirportCode.MSP to 2460.0,  // this route discontinued?
        AirportCode.FAI to AirportCode.SEA to 1533.0,  // calculated from booking flow total MQMs
        AirportCode.GDL to AirportCode.MEX to 286.0,   // estimated from airmilescalculator.com
        AirportCode.GDL to AirportCode.ORD to 1730.0,  // calculated from booking flow total MQMs
        AirportCode.GDL to AirportCode.SFO to 1641.0,  // calculated from booking flow total MQMs
        AirportCode.HNL to AirportCode.LAX to 2556.0,  // calculated from booking flow total MQMs
        AirportCode.HNL to AirportCode.SEA to 2677.0,  // calculated from booking flow total MQMs
        AirportCode.IAH to AirportCode.LAX to 1379.0,  // calculated from booking flow total MQMs
        AirportCode.IAH to AirportCode.MSP to 1034.0,  // calculated from booking flow total MQMs
        AirportCode.IAH to AirportCode.SLC to 1195.0,  // calculated from booking flow total MQMs
        AirportCode.ICN to AirportCode.JFK to 6906.0,  // calculated from booking flow total MQMs
        AirportCode.ICN to AirportCode.MSP to 6248.0,  // calculated from booking flow total MQMs
        //AirportCode.ICN to AirportCode.PVG to Double.NaN, // ??? can't seem to get good reading from Delta's booking flow
        AirportCode.ICN to AirportCode.SEA to 5217.0,  // calculated from booking flow total MQMs
        //AirportCode.ICN to AirportCode.SLC to 5886.0,
        AirportCode.ICN to AirportCode.SGN to 2208.0,  // estimated from booking flow total MQMs
        AirportCode.ICN to AirportCode.TPE to 902.0,   // estimated from booking flow total MQMs
        AirportCode.JFK to AirportCode.LAX to 2475.0,  // calculated from booking flow total MQMs
        AirportCode.JFK to AirportCode.SEA to 2421.0,  // calculated from booking flow total MQMs
        AirportCode.LAX to AirportCode.LIH to 2615.0,  // calculated from booking flow total MQMs
        AirportCode.LAX to AirportCode.MEX to 1553.0,  // calculated from booking flow total MQMs
        AirportCode.LAX to AirportCode.MSP to 1536.0,  // verified
        AirportCode.LAX to AirportCode.PVR to 1217.0,  // calculated from booking flow total MQMs
        AirportCode.LAX to AirportCode.SLC to 590.0,   // verified
        AirportCode.LIH to AirportCode.SEA to 2701.0,  // calculated from booking flow total MQMs
        AirportCode.LIM to AirportCode.MEX to 2629.0,  // estimated from airmilescalculator.com
        AirportCode.MCI to AirportCode.MSP to 393.0,   // estimated from airmilescalculator.com0
        AirportCode.MCI to AirportCode.SEA to 1489.0,  // calculated from booking flow total MQMs
        AirportCode.MEX to AirportCode.ORD to 1686.0,  // calculated from booking flow total MQMs
        AirportCode.MEX to AirportCode.SDQ to 1923.0,  // estimated from airmilescalculator.com
        AirportCode.MEX to AirportCode.SEA to 2332.0,  // calculated from booking flow total MQMs
        AirportCode.MEX to AirportCode.SFO to 1881.0,  // calculated from booking flow total MQMs
        AirportCode.MEX to AirportCode.SLC to 1657.0,  // calculated from booking flow total MQMs
        AirportCode.MEX to AirportCode.PVR to 411.0,   // estimated from airmilescalculator.com
        AirportCode.MSP to AirportCode.OMA to 281.0,   // estimated from Delta segment "flown miles"
        AirportCode.MSP to AirportCode.SEA to 1399.0,  // calculated from booking flow total MQMs
        AirportCode.OMA to AirportCode.SLC to 839.0,   // verified
        AirportCode.ORD to AirportCode.SLC to 1249.0,  // calculated from booking flow total MQMs
        AirportCode.PVR to AirportCode.SLC to 1440.0,  // calculated from booking flow total MQMs
        AirportCode.SEA to AirportCode.SLC to 689.0,   // calculated from booking flow total MQMs
    )

    putAll(distances)

    val unusedCodes = AirportCode.values().toMutableSet()

    for (entry in distances.entries) {
        val origin = entry.key.first
        val destination = entry.key.second
        val reverseSegment = destination to origin

        unusedCodes.remove(origin)
        unusedCodes.remove(destination)

        if (containsKey(reverseSegment)) {
            console.warn("Duplicate segment distance entry: ${origin}->${destination}")
        }
        this[reverseSegment] = entry.value
    }

    if (unusedCodes.isNotEmpty()) {
        console.warn("Airports with no segments: $unusedCodes")
    }
}

val availableDestinations: Map<AirportCode, Array<AirportCode>> = buildMap {
    segmentDistances.keys.groupBy(
        keySelector = Pair<AirportCode, AirportCode>::first,
        valueTransform = Pair<AirportCode, AirportCode>::second
    ).forEach { entry -> this@buildMap.put(entry.key, entry.value.toTypedArray()) }
}
