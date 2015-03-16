package com.lundberg.http.server;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.lundberg.http.ResponseTransformerImpl;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by stefanlundberg on 15-03-16.
 */

public class ResponseTransformerServer extends Server {

    public ResponseTransformerServer(WireMockServer mock) {
        super(mock, ServerType.TRANSFORMER);
        WireMockConfiguration extensions = new WireMockConfiguration().extensions(new ResponseTransformerImpl());
    }

    @Override
    public void setServerType(int statusCode, String body, String contentType) {
        getMock().givenThat(get(urlEqualTo(PATH))
                .willReturn(aResponse()
                        .withBody(body)
                        .withTransformers("responseTransformerImpl")));
    }


}
