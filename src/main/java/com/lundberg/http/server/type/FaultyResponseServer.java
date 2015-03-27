package com.lundberg.http.server.type;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;
import com.lundberg.http.server.Server;
import com.lundberg.http.server.ServerType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by stefanlundberg on 15-03-16.
 */
public class FaultyResponseServer extends Server {

    public FaultyResponseServer(WireMockServer mock) {
        super(mock, ServerType.FAULTY);
    }

    @Override
    public void configure(int statusCode, String body, String contentType) {
        getMock().givenThat(get(urlEqualTo(PATH))
                .willReturn(aResponse()
                        .withFault(Fault.EMPTY_RESPONSE)
                        .withHeader("Content-Type", contentType)
                        .withBody(body)));
    }
}
