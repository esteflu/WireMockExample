package com.lundberg.http.server;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by stefanlundberg on 15-03-16.
 */
public class SimpleServer extends Server {

    public SimpleServer(WireMockServer mock) {
        super(mock, ServerType.SIMPLE);
    }

    @Override
    public void setServerType(int statusCode, String body, String contentType) {
        getMock().givenThat(get(urlEqualTo(PATH))
                .willReturn(aResponse()
                        .withStatus(statusCode)
                        .withHeader("Content-Type", contentType)
                        .withBody(body)));
    }
}
