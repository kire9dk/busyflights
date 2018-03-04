package com.travix.medusa.busyflights.service.client.mock;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.service.client.CrazyAirClient;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CrazyAirMockClient implements CrazyAirClient {
    @Override
    public List<CrazyAirResponse> invokeClient(CrazyAirRequest crazyAirRequest) {
        return Arrays.asList(
                getCrazyAirResponse(
                        "B",
                        "2018-03-10T10:15:30",
                        "2018-03-11T10:15:30",
                        "SKP",
                        "AMS",
                        5000D,
                        "KLM"),
                getCrazyAirResponse(
                        "B",
                        "2018-03-10T10:15:30",
                        "2018-03-11T10:15:30",
                        "SKP",
                        "AMS",
                        2500D,
                        "KLM")
        );
    }

    public CrazyAirResponse getCrazyAirResponse(String cabinclass, String departureDate, String arrivalDate, String
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

}
