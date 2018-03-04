package com.travix.medusa.busyflights.domain.busyflights;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.travix.medusa.busyflights.jacson.LocalDateTimeSerializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BusyFlightsResponse {

    private String airline;
    private String supplier;
    private BigDecimal fare;
    private String departureAirportCode;
    private String destinationAirportCode;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime departureDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime arrivalDate;

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        if (fare != null) {
            fare.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        this.fare = fare;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public String getDestinationAirportCode() {
        return destinationAirportCode;
    }

    public void setDestinationAirportCode(String destinationAirportCode) {
        this.destinationAirportCode = destinationAirportCode;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
