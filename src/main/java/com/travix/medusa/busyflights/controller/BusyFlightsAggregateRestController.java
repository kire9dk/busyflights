package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.AggregateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/aggregates")
public class BusyFlightsAggregateRestController {

    private AggregateService aggregateService;

    public BusyFlightsAggregateRestController(AggregateService aggregateService) {
        this.aggregateService = aggregateService;
    }

    @RequestMapping(value = "/flights", method = GET, produces = APPLICATION_JSON_VALUE)
    public List<BusyFlightsResponse> getFlightOffers(@Valid BusyFlightsRequest busyFlightsRequest) {
        return aggregateService.invokeSuppliers(busyFlightsRequest);
    }
}
