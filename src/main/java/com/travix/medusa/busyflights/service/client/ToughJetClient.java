package com.travix.medusa.busyflights.service.client;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

import java.util.List;

public interface ToughJetClient extends HTTPClient<ToughJetRequest, List<ToughJetResponse>> {

    List<ToughJetResponse> invokeClient(ToughJetRequest crazyAirRequest);
}
