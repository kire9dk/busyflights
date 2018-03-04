package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.service.processor.supplier.SupplierProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.refEq;

@RunWith(MockitoJUnitRunner.class)
public class AggregateServiceTest {

    private AggregateService aggregateService;
    @Mock
    private SupplierProcessor<ToughJetRequest, ToughJetResponse> toughJetRequestToughJetResponseSupplierProcessor;
    @Mock
    private SupplierProcessor<CrazyAirRequest, CrazyAirResponse> crazyAirResponseSupplierProcessor;

    @Before
    public void setUp() throws Exception {
        aggregateService = new AggregateService(asList(toughJetRequestToughJetResponseSupplierProcessor, crazyAirResponseSupplierProcessor));
    }


    @Test
    public void invokeTests() {
        BusyFlightsRequest busyFlightsRequest = new BusyFlightsRequest();

        BusyFlightsResponse crazyAirResponse = new BusyFlightsResponse();
        crazyAirResponse.setSupplier("CrazyAir");

        BusyFlightsResponse thoughJet = new BusyFlightsResponse();
        thoughJet.setSupplier("ThoughJet");

        given(crazyAirResponseSupplierProcessor.processFlightsRequests(refEq(busyFlightsRequest)))
                .willReturn(asList(crazyAirResponse));
        given(toughJetRequestToughJetResponseSupplierProcessor.processFlightsRequests(refEq(busyFlightsRequest)))
                .willReturn(asList(thoughJet));

        List<BusyFlightsResponse> busyFlightsResponses = aggregateService.invokeSuppliers(busyFlightsRequest);

        assertThat(busyFlightsResponses, hasItems(crazyAirResponse, thoughJet));
    }
}