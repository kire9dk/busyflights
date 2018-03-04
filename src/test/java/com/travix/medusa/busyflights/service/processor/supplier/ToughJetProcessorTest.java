package com.travix.medusa.busyflights.service.processor.supplier;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.service.client.ToughJetClient;
import org.hamcrest.core.IsNull;
import org.hamcrest.number.BigDecimalCloseTo;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static com.travix.medusa.busyflights.testutils.ObjectCreators.createBusyFlightRequest;
import static com.travix.medusa.busyflights.testutils.ObjectCreators.getToughJetResponse;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ToughJetProcessorTest {

    private ToughJetProcessor toughJetProcessor;
    private ToughJetClient client;


    @Before
    public void setUp() throws Exception {
        client = mock(ToughJetClient.class);
        toughJetProcessor = new ToughJetProcessor(client);
    }

    @Test
    public void shouldTransformToToughJetRequest() {
        BusyFlightsRequest flightsRequest = createBusyFlightRequest(
                "SKP",
                "AMS",
                1,
                LocalDate.of(2018, 3, 7),
                LocalDate.of(2018, 3, 8)
        );

        ToughJetRequest toughJetRequest = toughJetProcessor.transformFlightsRequest(flightsRequest);
        assertThat(
                toughJetRequest,
                allOf(
                        hasProperty("from", is(flightsRequest.getOrigin())),
                        hasProperty("to", is(flightsRequest.getDestination())),
                        hasProperty("outboundDate", is("2018-03-07")),
                        hasProperty("inboundDate", is("2018-03-08")),
                        hasProperty("numberOfAdults", is(flightsRequest.getNumberOfPassengers()))
                )
        );
    }

    /**
     * private String carrier;
     * private double basePrice;
     * private double tax;
     * private double discount;
     * private String departureAirportName;
     * private String arrivalAirportName;
     * private String outboundDateTime;
     * private String inboundDateTime;
     */
    @Test
    public void shouldConvertBusyFlightsResponse() {
        ToughJetResponse toughJetResponse = getToughJetResponse("KLM",
                5000D,
                10D,
                0D,
                "SKP",
                "AMS",
                "2011-12-03T10:15:30.000Z",
                "2011-12-04T10:15:30.000Z");
        assertThat(
                toughJetProcessor.transformToFlightsResponse(Arrays.asList(toughJetResponse)),
                hasItem(
                        allOf(
                                hasProperty("airline", is(toughJetResponse.getCarrier())),
                                hasProperty("supplier", is("ToughJet")),
                                hasProperty("fare", BigDecimalCloseTo.closeTo(BigDecimal.valueOf(5500), BigDecimal.ZERO)),
                                hasProperty("departureAirportCode", is(toughJetResponse.getDepartureAirportName())),
                                hasProperty("destinationAirportCode", is(toughJetResponse.getArrivalAirportName())),
                                hasProperty("departureDate", is(LocalDateTime.of(2011, 12, 03, 10, 15, 30))),
                                hasProperty("arrivalDate", is(LocalDateTime.of(2011, 12, 04, 10, 15, 30)))
                        )
                )
        );
    }

    @Test
    public void shouldFailParsingOutboundDate() {
        ToughJetResponse toughJetResponse = getToughJetResponse("KLM",
                5000D,
                10D,
                0D,
                "SKP",
                "AMS",
                "random",
                "2011-12-04T10:15:30.000Z");
        assertThat(
                toughJetProcessor.transformToFlightsResponse(Arrays.asList(toughJetResponse)),
                hasItem(
                        allOf(
                                hasProperty("airline", is(toughJetResponse.getCarrier())),
                                hasProperty("supplier", is("ToughJet")),
                                hasProperty("fare", BigDecimalCloseTo.closeTo(BigDecimal.valueOf(5500), BigDecimal.ZERO)),
                                hasProperty("departureAirportCode", is(toughJetResponse.getDepartureAirportName())),
                                hasProperty("destinationAirportCode", is(toughJetResponse.getArrivalAirportName())),
                                hasProperty("departureDate", IsNull.nullValue(LocalDateTime.class)),
                                hasProperty("arrivalDate", is(LocalDateTime.of(2011, 12, 04, 10, 15, 30)))
                        )
                )
        );
    }

    @Test
    public void shouldSkipParsingOutboundDate() {
        ToughJetResponse toughJetResponse = getToughJetResponse("KLM",
                5000D,
                10D,
                0D,
                "SKP",
                "AMS",
                null,
                "2011-12-04T10:15:30.000Z");
        assertThat(
                toughJetProcessor.transformToFlightsResponse(Arrays.asList(toughJetResponse)),
                hasItem(
                        allOf(
                                hasProperty("airline", is(toughJetResponse.getCarrier())),
                                hasProperty("supplier", is("ToughJet")),
                                hasProperty("fare", BigDecimalCloseTo.closeTo(BigDecimal.valueOf(5500), BigDecimal.ZERO)),
                                hasProperty("departureAirportCode", is(toughJetResponse.getDepartureAirportName())),
                                hasProperty("destinationAirportCode", is(toughJetResponse.getArrivalAirportName())),
                                hasProperty("departureDate", IsNull.nullValue(LocalDateTime.class)),
                                hasProperty("arrivalDate", is(LocalDateTime.of(2011, 12, 04, 10, 15, 30)))
                        )
                )
        );
    }

    @Test
    public void shouldFailParsingInboundDate() {
        ToughJetResponse toughJetResponse = getToughJetResponse("KLM",
                5000D,
                10D,
                0D,
                "SKP",
                "AMS",
                "2011-12-03T10:15:30.000Z",
                "random");
        assertThat(
                toughJetProcessor.transformToFlightsResponse(Arrays.asList(toughJetResponse)),
                hasItem(
                        allOf(
                                hasProperty("airline", is(toughJetResponse.getCarrier())),
                                hasProperty("supplier", is("ToughJet")),
                                hasProperty("fare", BigDecimalCloseTo.closeTo(BigDecimal.valueOf(5500), BigDecimal.ZERO)),
                                hasProperty("departureAirportCode", is(toughJetResponse.getDepartureAirportName())),
                                hasProperty("destinationAirportCode", is(toughJetResponse.getArrivalAirportName())),
                                hasProperty("departureDate", is(LocalDateTime.of(2011, 12, 03, 10, 15, 30))),
                                hasProperty("arrivalDate", IsNull.nullValue(LocalDateTime.class))
                        )
                )
        );
    }

    @Test
    public void shouldSkipParsingInboundDate() {
        ToughJetResponse toughJetResponse = getToughJetResponse("KLM",
                5000D,
                10D,
                0D,
                "SKP",
                "AMS",
                "2011-12-03T10:15:30.000Z",
                null);
        assertThat(
                toughJetProcessor.transformToFlightsResponse(Arrays.asList(toughJetResponse)),
                hasItem(
                        allOf(
                                hasProperty("airline", is(toughJetResponse.getCarrier())),
                                hasProperty("supplier", is("ToughJet")),
                                hasProperty("fare", BigDecimalCloseTo.closeTo(BigDecimal.valueOf(5500), BigDecimal.ZERO)),
                                hasProperty("departureAirportCode", is(toughJetResponse.getDepartureAirportName())),
                                hasProperty("destinationAirportCode", is(toughJetResponse.getArrivalAirportName())),
                                hasProperty("departureDate", is(LocalDateTime.of(2011, 12, 03, 10, 15, 30))),
                                hasProperty("arrivalDate", IsNull.nullValue(LocalDateTime.class))
                        )
                )
        );
    }


}