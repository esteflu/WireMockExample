package com.lundberg.http.server.type;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.lundberg.http.server.Server;
import com.lundberg.http.server.ServerType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by stefanlundberg on 15-03-16.
 */

public class ResponseTransformerServer extends Server {

    private final String transformerName;

    public ResponseTransformerServer(WireMockServer mock, String transformerName) {
        super(mock, ServerType.TRANSFORMER);
        this.transformerName = transformerName;
    }

    @Override
    public void configure(int statusCode, String body, String contentType) {
        getMock().givenThat(get(urlEqualTo(PATH))
                .willReturn(aResponse()
                        .withHeader("Content-Type", contentType)
                        .withBody(body)
                        .withTransformers(transformerName)));
    }


}
