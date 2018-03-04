package com.travix.medusa.busyflights.service.client;

public interface HTTPClient<RequestPayload, ResponsePayload> {
    ResponsePayload invokeClient(RequestPayload crazyAirRequest);
}
