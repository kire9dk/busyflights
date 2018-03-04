package com.travix.medusa.busyflights.service.processor.supplier;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.client.HTTPClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SupplierProcessorTest {

    @Test
    public void shouldInvokeMethodInCorrectOrder() {
        ArrayList<BusyFlightsResponse> busyFlightsResponses = new ArrayList<>();
        HTTPClient<String, String> stringStringHTTPClient = new HTTPClient<String, String>() {
            @Override
            public String invokeClient(String flightsRequest) {
                if ("transform".equals(flightsRequest)) {
                    return "revoke";
                } else {
                    return null;
                }
            }
        };
        SupplierProcessor<String, String> supplierProcessor = new SupplierProcessor<String, String>
                (stringStringHTTPClient) {
            @Override
            protected String transformFlightsRequest(BusyFlightsRequest flightsRequest) {
                return "transform";
            }

            @Override
            protected List<BusyFlightsResponse> transformToFlightsResponse(String flightsResponse) {
                if ("revoke".equals(flightsResponse)) {
                    return busyFlightsResponses;
                } else {
                    return null;
                }
            }
        };
        List<BusyFlightsResponse> responses = supplierProcessor.processFlightsRequests(new BusyFlightsRequest());
        assertEquals(responses, busyFlightsResponses);
    }

}