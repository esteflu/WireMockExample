package com.lundberg.http.server;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by stefanlundberg on 15-03-16.
 */
public class FaultResponseServer extends Server {

    public FaultResponseServer(WireMockServer mock) {
        super(mock, ServerType.FAULTY);
    }

    @Override
    public void setServerType(int statusCode, String body, String contentType) {
        getMock().givenThat(get(urlEqualTo(PATH))
                .willReturn(aResponse()
                        .withFault(Fault.EMPTY_RESPONSE)
                        .withHeader("Content-Type", contentType)
                        .withBody(body)));
    }
}
