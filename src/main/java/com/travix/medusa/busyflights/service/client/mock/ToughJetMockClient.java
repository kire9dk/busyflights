package com.travix.medusa.busyflights.service.client.mock;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.service.client.ToughJetClient;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ToughJetMockClient implements ToughJetClient {
    @Override
    public List<ToughJetResponse> invokeClient(ToughJetRequest crazyAirRequest) {
        return Arrays.asList(
                getToughJetResponse("KLM",
                        5000D,
                        10D,
                        0D,
                        "SKP",
                        "AMS",
                        "2018-03-10T10:15:30.000Z",
                        "2018-03-11T10:15:30.000Z"),
                getToughJetResponse("KLM",
                        1000D,
                        10D,
                        0D,
                        "SKP",
                        "AMS",
                        "2018-03-10T10:15:30.000Z",
                        "2018-03-11T10:15:30.000Z")
        );
    }

    public static ToughJetResponse getToughJetResponse(String carrier, double basePrice, double tax, double discount, String departureAirportName, String arrivalAirportName, String outboundDateTime, String inboundDateTime) {
        ToughJetResponse toughJetResponse = new ToughJetResponse();
        toughJetResponse.setCarrier(carrier);
        toughJetResponse.setBasePrice(basePrice);
        toughJetResponse.setTax(tax);
        toughJetResponse.setDiscount(discount);
        toughJetResponse.setDepartureAirportName(departureAirportName);
        toughJetResponse.setArrivalAirportName(arrivalAirportName);
        toughJetResponse.setOutboundDateTime(outboundDateTime);
        toughJetResponse.setInboundDateTime(inboundDateTime);
        return toughJetResponse;
    }
}
