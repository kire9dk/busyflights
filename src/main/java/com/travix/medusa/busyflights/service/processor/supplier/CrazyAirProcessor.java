package com.travix.medusa.busyflights.service.processor.supplier;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.service.client.CrazyAirClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

@Service
public class CrazyAirProcessor extends SupplierProcessor<CrazyAirRequest, List<CrazyAirResponse>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrazyAirProcessor.class);
    private static final String SUPPLIER = "CrazyAir";

    public CrazyAirProcessor(CrazyAirClient crazyAirClient) {
        super(crazyAirClient);
    }

    @Override
    protected CrazyAirRequest transformFlightsRequest(BusyFlightsRequest flightsRequest) {
        CrazyAirRequest crazyAirRequest = new CrazyAirRequest();
        crazyAirRequest.setOrigin(flightsRequest.getOrigin());
        crazyAirRequest.setDestination(flightsRequest.getDestination());
        crazyAirRequest.setDepartureDate(flightsRequest.getDepartureDate().format(ISO_LOCAL_DATE));
        crazyAirRequest.setReturnDate(flightsRequest.getReturnDate().format(ISO_LOCAL_DATE));
        crazyAirRequest.setPassengerCount(flightsRequest.getNumberOfPassengers());
        return crazyAirRequest;
    }

    @Override
    protected List<BusyFlightsResponse> transformToFlightsResponse(List<CrazyAirResponse> flightsResponse) {
        return flightsResponse
                .stream()
                .map(this::getSingleBusyFlightsResponse)
                .collect(Collectors.toList());
    }

    private BusyFlightsResponse getSingleBusyFlightsResponse(CrazyAirResponse crazyAirResponse) {
        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
        busyFlightsResponse.setAirline(crazyAirResponse.getAirline());
        busyFlightsResponse.setSupplier(SUPPLIER);
        busyFlightsResponse.setFare(BigDecimal.valueOf(crazyAirResponse.getPrice()));
        busyFlightsResponse.setDepartureAirportCode(crazyAirResponse.getDepartureAirportCode());
        busyFlightsResponse.setDestinationAirportCode(crazyAirResponse.getDestinationAirportCode());
        try {
            if (crazyAirResponse.getDepartureDate() != null) {
                busyFlightsResponse.setDepartureDate(LocalDateTime.parse(crazyAirResponse.getDepartureDate(), ISO_LOCAL_DATE_TIME));
            }
        } catch (DateTimeParseException e) {
            LOGGER.warn("Unable to parse departure date", e);
        }
        try {
            if (crazyAirResponse.getArrivalDate() != null) {
                busyFlightsResponse.setArrivalDate(LocalDateTime.parse(crazyAirResponse.getArrivalDate(), ISO_LOCAL_DATE_TIME));
            }
        } catch (DateTimeParseException e) {
            LOGGER.warn("Unable to parse arrival date", e);
        }
        return busyFlightsResponse;
    }

}
