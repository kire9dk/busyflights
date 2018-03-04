package com.travix.medusa.busyflights.service.processor.supplier;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.client.HTTPClient;

import java.util.List;

public abstract class SupplierProcessor<Request, Response> {

    private HTTPClient<Request, Response> client;

    public SupplierProcessor(HTTPClient<Request, Response> client) {
        this.client = client;
    }

    protected abstract Request transformFlightsRequest(BusyFlightsRequest flightsRequest);

    protected abstract List<BusyFlightsResponse> transformToFlightsResponse(Response flightsResponse);

    protected Response invokeSupplier(Request flightsRequest) {
        return client.invokeClient(flightsRequest);
    }

    public List<BusyFlightsResponse> processFlightsRequests(BusyFlightsRequest flightsRequest) {
        return transformToFlightsResponse(invokeSupplier(transformFlightsRequest(flightsRequest)));
    }
}
