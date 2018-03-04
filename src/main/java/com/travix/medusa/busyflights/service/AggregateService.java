package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.processor.supplier.SupplierProcessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AggregateService {

    private List<SupplierProcessor> supplierProcessors;

    public AggregateService(List<SupplierProcessor> supplierProcessors) {
        this.supplierProcessors = supplierProcessors;
    }

    public List<BusyFlightsResponse> invokeSuppliers(BusyFlightsRequest busyFlightsRequest) {
        List<BusyFlightsResponse> busyFlightsResponses = new ArrayList<>();
        for (SupplierProcessor<?, ?> supplierProcessor : supplierProcessors) {
            List<BusyFlightsResponse> flightsResponses = supplierProcessor.processFlightsRequests(busyFlightsRequest);
            if (flightsResponses != null && !flightsResponses.isEmpty()) {
                busyFlightsResponses.addAll(flightsResponses);
            }
        }
        return busyFlightsResponses;
    }
}
