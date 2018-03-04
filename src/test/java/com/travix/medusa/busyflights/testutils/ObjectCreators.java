package com.travix.medusa.busyflights.testutils;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

import java.time.LocalDate;

public class ObjectCreators {

    public static CrazyAirResponse getCrazyAirResponse(String cabinclass, String departureDate, String arrivalDate, String
            departureAirportCode, String destinationAirportCode, double price, String airline) {
        CrazyAirResponse crazyAirResponse = new CrazyAirResponse();

        crazyAirResponse.setAirline(airline);
        crazyAirResponse.setPrice(price);
        crazyAirResponse.setDestinationAirportCode(destinationAirportCode);
        crazyAirResponse.setDepartureAirportCode(departureAirportCode);
        crazyAirResponse.setArrivalDate(arrivalDate);
        crazyAirResponse.setDepartureDate(departureDate);
        crazyAirResponse.setCabinclass(cabinclass);
        return crazyAirResponse;
    }

    public static BusyFlightsRequest createBusyFlightRequest(String origin, String destination, int numberOfPassengers,
                                                             LocalDate departureDate, LocalDate returnDate) {
        BusyFlightsRequest flightsRequest = new BusyFlightsRequest();
        flightsRequest.setDepartureDate(departureDate);
        flightsRequest.setReturnDate(returnDate);
        flightsRequest.setNumberOfPassengers(numberOfPassengers);
        flightsRequest.setOrigin(origin);
        flightsRequest.setDestination(destination);
        return flightsRequest;
    }

    public static ToughJetResponse getToughJetResponse(String carrier, double basePrice, double tax, double discount, String departureAirportName, String arrivalAirportName, String outboundDateTime, String inboundDateTime) {
        ToughJetResponse toughJetResponse = new ToughJetResponse();
        toughJetResponse.setCarrier(carrier);
        toughJetResponse.setBasePrice(basePrice);
        toughJetResponse.setTax(tax);
        toughJetResponse.setDiscount(discount);
        toughJetResponse.setDepartureAirportName(departureAirportName);
        toughJetResponse.setArrivalAirportName(arrivalAirportName);
        toughJetResponse.setOutboundDateTime(outboundDateTime);
        toughJetResponse.setInboundDateTime(inboundDateTime);
        return toughJetResponse;
    }
}
