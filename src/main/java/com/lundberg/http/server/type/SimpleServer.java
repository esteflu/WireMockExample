package com.lundberg.http.server.type;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.lundberg.http.server.Server;
import com.lundberg.http.server.ServerType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by stefanlundberg on 15-03-16.
 */
public class SimpleServer extends Server {

    public SimpleServer(WireMockServer mock) {
        super(mock, ServerType.SIMPLE);
    }

    @Override
    public void configure(int statusCode, String body, String contentType) {
        getMock().givenThat(get(urlEqualTo(PATH))
                .willReturn(aResponse()
                        .withStatus(statusCode)
                        .withHeader("Content-Type", contentType)
                        .withBody(body)));
    }
}
