package com.travix.medusa.busyflights.service.processor.supplier;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.service.client.CrazyAirClient;
import org.hamcrest.core.IsNull;
import org.hamcrest.number.BigDecimalCloseTo;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static com.travix.medusa.busyflights.testutils.ObjectCreators.createBusyFlightRequest;
import static com.travix.medusa.busyflights.testutils.ObjectCreators.getCrazyAirResponse;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CrazyAirProcessorTest {


    private CrazyAirProcessor crazyAirProcessor;
    private CrazyAirClient crazyAirClient;

    @Before
    public void setUp() throws Exception {
        crazyAirClient = mock(CrazyAirClient.class);
        crazyAirProcessor = new CrazyAirProcessor(crazyAirClient);
    }

    @Test
    public void shouldConvertBusyFlightsRequest() {
        BusyFlightsRequest flightsRequest = createBusyFlightRequest(
                "SKP",
                "AMS",
                1,
                LocalDate.of(2018, 3, 7),
                LocalDate.of(2018, 3, 8)
        );

        assertThat(
                crazyAirProcessor.transformFlightsRequest(flightsRequest),
                allOf(
                        hasProperty("origin", is(flightsRequest.getOrigin())),
                        hasProperty("destination", is(flightsRequest.getDestination())),
                        hasProperty("departureDate", is("2018-03-07")),
                        hasProperty("returnDate", is("2018-03-08")),
                        hasProperty("passengerCount", is(flightsRequest.getNumberOfPassengers()))
                )
        );
    }

    @Test
    public void shouldConvertBusyFlightsResponse() {
        CrazyAirResponse crazyAirResponse = getCrazyAirResponse(
                "B",
                "2011-12-03T10:15:30",
                "2011-12-04T10:15:30",
                "SKP",
                "AMS",
                5000D,
                "KLM");

        assertThat(
                crazyAirProcessor.transformToFlightsResponse(Arrays.asList(crazyAirResponse)),
                hasItem(
                        allOf(
                                hasProperty("airline", is(crazyAirResponse.getAirline())),
                                hasProperty("supplier", is("CrazyAir")),
                                hasProperty("fare", BigDecimalCloseTo.closeTo(BigDecimal.valueOf(5000), BigDecimal.ZERO)),
                                hasProperty("departureAirportCode", is(crazyAirResponse.getDepartureAirportCode())),
                                hasProperty("destinationAirportCode", is(crazyAirResponse.getDestinationAirportCode())),
                                hasProperty("departureDate", is(LocalDateTime.of(2011, 12, 03, 10, 15, 30))),
                                hasProperty("arrivalDate", is(LocalDateTime.of(2011, 12, 04, 10, 15, 30)))
                        )
                )
        );
    }

    @Test
    public void shouldFailParsingDepartureDate() {
        CrazyAirResponse crazyAirResponse = getCrazyAirResponse(
                "B",
                "random",
                "2011-12-04T10:15:30",
                "SKP",
                "AMS",
                5000D,
                "KLM");

        assertThat(
                crazyAirProcessor.transformToFlightsResponse(Arrays.asList(crazyAirResponse)),
                hasItem(
                        allOf(
                                hasProperty("airline", is(crazyAirResponse.getAirline())),
                                hasProperty("supplier", is("CrazyAir")),
                                hasProperty("fare", BigDecimalCloseTo.closeTo(BigDecimal.valueOf(5000), BigDecimal.ZERO)),
                                hasProperty("departureAirportCode", is(crazyAirResponse.getDepartureAirportCode())),
                                hasProperty("destinationAirportCode", is(crazyAirResponse.getDestinationAirportCode())),
                                hasProperty("departureDate", IsNull.nullValue(LocalDateTime.class)),
                                hasProperty("arrivalDate", is(LocalDateTime.of(2011, 12, 04, 10, 15, 30)))
                        )
                )
        );
    }

    @Test
    public void shouldNotParseDepartureDate() {
        CrazyAirResponse crazyAirResponse = getCrazyAirResponse(
                "B",
                null,
                "2011-12-04T10:15:30",
                "SKP",
                "AMS",
                5000D,
                "KLM");

        assertThat(
                crazyAirProcessor.transformToFlightsResponse(Arrays.asList(crazyAirResponse)),
                hasItem(
                        allOf(
                                hasProperty("airline", is(crazyAirResponse.getAirline())),
                                hasProperty("supplier", is("CrazyAir")),
                                hasProperty("fare", BigDecimalCloseTo.closeTo(BigDecimal.valueOf(5000), BigDecimal.ZERO)),
                                hasProperty("departureAirportCode", is(crazyAirResponse.getDepartureAirportCode())),
                                hasProperty("destinationAirportCode", is(crazyAirResponse.getDestinationAirportCode())),
                                hasProperty("departureDate", IsNull.nullValue(LocalDateTime.class)),
                                hasProperty("arrivalDate", is(LocalDateTime.of(2011, 12, 04, 10, 15, 30))))
                )
        );
    }

    @Test
    public void shouldFailParsingArravilDate() {
        CrazyAirResponse crazyAirResponse = getCrazyAirResponse(
                "B",
                "2011-12-03T10:15:30",
                "random",
                "SKP",
                "AMS",
                5000D,
                "KLM");

        assertThat(
                crazyAirProcessor.transformToFlightsResponse(Arrays.asList(crazyAirResponse)),
                hasItem(
                        allOf(
                                hasProperty("airline", is(crazyAirResponse.getAirline())),
                                hasProperty("supplier", is("CrazyAir")),
                                hasProperty("fare", BigDecimalCloseTo.closeTo(BigDecimal.valueOf(5000), BigDecimal.ZERO)),
                                hasProperty("departureAirportCode", is(crazyAirResponse.getDepartureAirportCode())),
                                hasProperty("destinationAirportCode", is(crazyAirResponse.getDestinationAirportCode())),
                                hasProperty("departureDate", is(LocalDateTime.of(2011, 12, 03, 10, 15, 30))),
                                hasProperty("arrivalDate", IsNull.nullValue(LocalDateTime.class))
                        )
                )
        );
    }

    @Test
    public void shouldNotParseArravilDate() {
        CrazyAirResponse crazyAirResponse = getCrazyAirResponse(
                "B",
                "2011-12-03T10:15:30",
                null,
                "SKP",
                "AMS",
                5000D,
                "KLM");

        assertThat(
                crazyAirProcessor.transformToFlightsResponse(Arrays.asList(crazyAirResponse)),
                hasItem(
                        allOf(
                                hasProperty("airline", is(crazyAirResponse.getAirline())),
                                hasProperty("supplier", is("CrazyAir")),
                                hasProperty("fare", BigDecimalCloseTo.closeTo(BigDecimal.valueOf(5000), BigDecimal.ZERO)),
                                hasProperty("departureAirportCode", is(crazyAirResponse.getDepartureAirportCode())),
                                hasProperty("destinationAirportCode", is(crazyAirResponse.getDestinationAirportCode())),
                                hasProperty("departureDate", is(LocalDateTime.of(2011, 12, 03, 10, 15, 30))),
                                hasProperty("arrivalDate", IsNull.nullValue(LocalDateTime.class))
                        )
                )
        );
    }

    @Test
    public void shouldInvokeCrazyAirClient() {
        CrazyAirResponse crazyAirResponse = getCrazyAirResponse(
                "B",
                "2011-12-03T10:15:30",
                "2011-12-04T10:15:30",
                "SKP",
                "AMS",
                5000D,
                "KLM");

        CrazyAirRequest flightsRequest = new CrazyAirRequest();
        given(crazyAirClient.invokeClient(flightsRequest))
                .willReturn(Arrays.asList(crazyAirResponse));
        assertThat(
                crazyAirProcessor.invokeSupplier(flightsRequest),
                hasItem(
                        crazyAirResponse
                )
        );
    }


}