package com.travix.medusa.busyflights.service.processor.supplier;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

import java.util.List;

public interface SupplierProcessorInterface {
    List<BusyFlightsResponse> processFlightsRequests(BusyFlightsRequest flightsRequest);
}
