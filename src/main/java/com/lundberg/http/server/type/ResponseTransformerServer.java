package com.lundberg.http.server.type;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.lundberg.http.server.Server;
import com.lundberg.http.server.ServerType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by stefanlundberg on 15-03-16.
 */

public class ResponseTransformerServer extends Server {

    public ResponseTransformerServer(WireMockServer mock) {
        super(mock, ServerType.TRANSFORMER);
    }

    @Override
    public void configure(int statusCode, String body, String contentType) {
        getMock().givenThat(get(urlEqualTo(PATH))
                .willReturn(aResponse()
                        .withBody(body)
                        .withTransformers("responseTransformerImpl")));
    }


}
