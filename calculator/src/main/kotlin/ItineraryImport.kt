import org.w3c.dom.url.URL

typealias ItineraryImporter = (importData: String) -> Itinerary?

object AirFranceAndKlmImporter {
    private val SEGMENT_ORIGIN_REGEX: Regex = Regex(
        "(?<airport>[A-Z]{3})" +
            ":" +
            "[AC]" +
            ":" +
            "(?<year>\\d{4})" +
            "(?<month>\\d{2})" +
            "(?<day>\\d{2})" +
            "@" +
            "(?<hour>\\d{2})" +
            "(?<minute>\\d{2})" +
            ":" +
            "(?<marketingAirline>[A-Z]{2})" +
            "(?<marketingFlightNumber>\\d{4})" +
            ":" +
            "(?<fareClass>[A-Z])" +
            ":" +
            "(?<fareBasis>[\\dA-Z]+)" +
            ":" +
            "(?<cabinClass>[A-Za-z]+)" +
            "(?:%3E|>)"
    )

    private val SEGMENT_DESTINATION_REGEX = Regex(
        "(?<airport>[A-Z]{3})" +
            ":" +
            "A$"
    )

    private val domainToSellerMap = mapOf(
        "wwws.airfrance.us" to TicketSeller.AF,
        "www.klm.us" to TicketSeller.KL
    )

    private val sellerToIssuerMap = mapOf(
        TicketSeller.AF to Airline.AF_INTL,
        TicketSeller.KL to Airline.KL,
    )

    private fun parseAirportCode(matchResult: MatchResult) = try {
        AirportCode.valueOf(matchResult.groups["airport"]!!.value)
    } catch (e: IllegalArgumentException) {
        null
    }

    fun import(importData: String): Itinerary? {
        val importDataUrl = URL(importData)
        val ticketSeller = domainToSellerMap[importDataUrl.hostname] ?: TicketSeller.AF
        val ticketIssuer = sellerToIssuerMap[ticketSeller] ?: Airline.AF_INTL

        val segments = importDataUrl.searchParams.getAll("connections").first()
            .split('-')
            .flatMap { segmentSetString ->
                val segments: MutableList<ItinerarySegment> = mutableListOf()

                var nextStart = 0

                var nextOrigin: AirportCode? = null
                var nextMarketingAirline: Airline? = null
                var nextFlightNumber: Int? = null
                var nextFareClass: FareClass? = null

                while (true) {
                    val matchResult = SEGMENT_ORIGIN_REGEX.matchAt(segmentSetString, nextStart)
                        ?: break

                    nextStart = matchResult.range.last + 1

                    val destinationAirportCode = parseAirportCode(matchResult)

                    if (nextMarketingAirline != null
                        && nextFlightNumber != null
                    ) {
                        segments.add(
                            ItinerarySegment(
                                origin = nextOrigin,
                                destination = destinationAirportCode,
                                fareClass = nextFareClass,
                                marketedBy = nextMarketingAirline,
                                marketedFlightNumber = nextFlightNumber,
                                ticketedBy = ticketIssuer,
                                operatedBy = nextMarketingAirline
                            )
                        )
                    }

                    nextOrigin = destinationAirportCode

                    nextMarketingAirline = try {
                        Airline.valueOf(matchResult.groups["marketingAirline"]!!.value)
                    } catch (e: IllegalArgumentException) {
                        Airline.values().first()
                    }

                    nextFlightNumber = matchResult.groups["marketingFlightNumber"]!!.value.toIntOrNull(10)
                    nextFareClass = FareClass.valueOf(matchResult.groups["fareClass"]!!.value)
                }

                val destinationMatch = SEGMENT_DESTINATION_REGEX.matchAt(segmentSetString, nextStart)
                    ?: return null

                segments.add(
                    ItinerarySegment(
                        origin = nextOrigin,
                        destination = parseAirportCode(destinationMatch),
                        fareClass = nextFareClass,
                        marketedBy = nextMarketingAirline,
                        marketedFlightNumber = nextFlightNumber,
                        ticketedBy = ticketIssuer,
                        operatedBy = nextMarketingAirline
                    )
                )

                return@flatMap segments
            }

        return Itinerary(
            medallionStatus = MedallionStatus.NONE,
            segments = segments.toTypedArray(),
            soldBy = ticketSeller,
            baseCost = 0.0,
            airlineFees = null,
            governmentFeesAndTaxes = null,
            creditCard = CreditCard.OTHER
        )
    }
}

val itineraryImporters: Map<TicketSeller, ItineraryImporter> = mapOf(
    TicketSeller.AF to AirFranceAndKlmImporter::import,
    TicketSeller.KL to AirFranceAndKlmImporter::import
)
