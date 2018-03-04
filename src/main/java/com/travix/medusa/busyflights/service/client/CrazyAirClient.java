package com.travix.medusa.busyflights.service.client;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;

import java.util.List;

public interface CrazyAirClient extends HTTPClient<CrazyAirRequest, List<CrazyAirResponse>>{
    List<CrazyAirResponse> invokeClient(CrazyAirRequest crazyAirRequest);
}
