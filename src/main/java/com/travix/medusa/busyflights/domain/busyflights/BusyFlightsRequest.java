package com.travix.medusa.busyflights.domain.busyflights;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class BusyFlightsRequest {

    @NotNull
    @Size(min = 3, max = 3)
    private String origin;
    @NotNull
    @Size(min = 3, max = 3)
    private String destination;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;
    @NotNull
    @Min(1)
    private int numberOfPassengers;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(final String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(final String destination) {
        this.destination = destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(final LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(final LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(final int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }
}
