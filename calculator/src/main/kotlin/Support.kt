@file:Suppress("unused")

@JsName("getAvailableDestinations")
fun getAvailableDestinations(airportCode: AirportCode?): Array<AirportCode> {
    return availableDestinations[airportCode] ?: arrayOf()
}

@JsName("getFareRules")
fun getFareRulesForAirline(airline: Airline, fareClass: FareClass): FlightEarningRules? = airline.fareRules[fareClass]
