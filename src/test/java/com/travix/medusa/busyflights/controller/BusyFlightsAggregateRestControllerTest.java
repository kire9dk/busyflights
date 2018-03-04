package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.service.AggregateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BusyFlightsAggregateRestController.class)
public class BusyFlightsAggregateRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AggregateService aggregateService;

    @Test
    public void shouldInvokeControllerAndValidatePayload() throws Exception {
        mvc.perform(get("/api/aggregates/flights")
                .param("origin", "SKP")
                .param("destination", "AMS")
                .param("numberOfPassengers", "1")
                .param("departureDate", "2011-12-03")
                .param("returnDate", "2011-12-03")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        //TODO check simple response
    }

    @Test
    public void shouldFailWhenNoOriginProvided() throws Exception {
        mvc.perform(get("/api/aggregates/flights")
                .param("destination", "AMS")
                .param("numberOfPassengers", "1")
                .param("departureDate", "2011-12-03")
                .param("returnDate", "2011-12-03")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFailWhenNoDestinationProvided() throws Exception {
        mvc.perform(get("/api/aggregates/flights")
                .param("origin", "SKP")
                .param("numberOfPassengers", "1")
                .param("departureDate", "2011-12-03")
                .param("returnDate", "2011-12-03")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFailWhenNoNumberOfPassenegersProvided() throws Exception {
        mvc.perform(get("/api/aggregates/flights")
                .param("origin", "SKP")
                .param("destination", "AMS")
                .param("departureDate", "2011-12-03")
                .param("returnDate", "2011-12-03")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFailWhenInvalidNumberOfPassenegersProvided() throws Exception {
        mvc.perform(get("/api/aggregates/flights")
                .param("origin", "SKP")
                .param("destination", "AMS")
                .param("numberOfPassengers", "0")
                .param("departureDate", "2011-12-03")
                .param("returnDate", "2011-12-03")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFailWhenNoDepartureDateProvided() throws Exception {
        mvc.perform(get("/api/aggregates/flights")
                .param("origin", "SKP")
                .param("destination", "AMS")
                .param("numberOfPassengers", "1")
                .param("returnDate", "2011-12-03")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFailWhenNoReturnDateProvided() throws Exception {
        mvc.perform(get("/api/aggregates/flights")
                .param("origin", "SKP")
                .param("destination", "AMS")
                .param("numberOfPassengers", "1")
                .param("departureDate", "2011-12-03")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFailOnBadDepartureDate() throws Exception {
        mvc.perform(get("/api/aggregates/flights")
                .param("origin", "SKP")
                .param("destination", "AMS")
                .param("numberOfPassengers", "1")
                .param("departureDate", "2011-13-03")
                .param("returnDate", "2011-12-03")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFailOnBadReturnDate() throws Exception {
        mvc.perform(get("/api/aggregates/flights")
                .param("origin", "SKP")
                .param("destination", "AMS")
                .param("numberOfPassengers", "1")
                .param("departureDate", "2011-12-03")
                .param("returnDate", "2011-13-03")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}