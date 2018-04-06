package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.processor.supplier.SupplierProcessorInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AggregateService {

    private List<SupplierProcessorInterface> supplierProcessors;

    public AggregateService(List<SupplierProcessorInterface> supplierProcessors) {
        this.supplierProcessors = supplierProcessors;
    }

    public List<BusyFlightsResponse> invokeSuppliers(BusyFlightsRequest busyFlightsRequest) {
        return supplierProcessors
                .parallelStream()
                .flatMap(supplierProcessor -> supplierProcessor.processFlightsRequests(busyFlightsRequest).stream())
                .collect(Collectors.toList());
    }
}
