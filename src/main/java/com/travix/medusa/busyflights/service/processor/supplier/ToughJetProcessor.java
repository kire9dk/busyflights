package com.travix.medusa.busyflights.service.processor.supplier;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.service.client.ToughJetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Service
public class ToughJetProcessor extends SupplierProcessor<ToughJetRequest, List<ToughJetResponse>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToughJetProcessor.class);
    private static final String SUPPLIER = "ToughJet";
    private static final double MAX_PERCENTS_COUNT = 100D;

    public ToughJetProcessor(ToughJetClient toughJetClient) {
        super(toughJetClient);
    }

    @Override
    protected ToughJetRequest transformFlightsRequest(BusyFlightsRequest flightsRequest) {
        ToughJetRequest toughJetRequest = new ToughJetRequest();
        toughJetRequest.setFrom(flightsRequest.getOrigin());
        toughJetRequest.setTo(flightsRequest.getDestination());
        toughJetRequest.setOutboundDate(flightsRequest.getDepartureDate().format(ISO_LOCAL_DATE));
        toughJetRequest.setInboundDate(flightsRequest.getReturnDate().format(ISO_LOCAL_DATE));
        toughJetRequest.setNumberOfAdults(flightsRequest.getNumberOfPassengers());
        return toughJetRequest;
    }

    @Override
    protected List<BusyFlightsResponse> transformToFlightsResponse(List<ToughJetResponse> flightsResponse) {
        return flightsResponse
                .stream()
                .map(this::getBusyFlightsResponse)
                .collect(Collectors.toList());
    }

    private BusyFlightsResponse getBusyFlightsResponse(ToughJetResponse toughJetResponse) {
        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
        busyFlightsResponse.setSupplier(SUPPLIER);
        busyFlightsResponse.setAirline(toughJetResponse.getCarrier());
        busyFlightsResponse.setFare(BigDecimal.valueOf(getFare(toughJetResponse)));
        busyFlightsResponse.setDepartureAirportCode(toughJetResponse.getDepartureAirportName());
        busyFlightsResponse.setDestinationAirportCode(toughJetResponse.getArrivalAirportName());
        try {
            if (toughJetResponse.getOutboundDateTime() != null) {
                Instant from = Instant.from(ISO_INSTANT.parse(toughJetResponse.getOutboundDateTime()));
                busyFlightsResponse.setDepartureDate(LocalDateTime.ofInstant(from, ZoneId.of("UTC")));
            }
        } catch (DateTimeParseException e) {
            LOGGER.warn("Unable to parse departure date", e);
        }
        try {
            if (toughJetResponse.getInboundDateTime() != null) {
                Instant from = Instant.from(ISO_INSTANT.parse(toughJetResponse.getInboundDateTime()));
                busyFlightsResponse.setArrivalDate(LocalDateTime.ofInstant(from, ZoneId.of("UTC")));
            }
        } catch (DateTimeParseException e) {
            LOGGER.warn("Unable to parse departure date", e);
        }
        return busyFlightsResponse;
    }

    private double getFare(ToughJetResponse toughJetResponse) {
        // assumption: the tax and discount are values in the interval [0-100%]
        return (toughJetResponse.getBasePrice()
                * (1 + (toughJetResponse.getTax() / MAX_PERCENTS_COUNT))
                * (1 - (toughJetResponse.getDiscount() / MAX_PERCENTS_COUNT))
        );
    }
}
