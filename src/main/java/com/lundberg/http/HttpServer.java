package com.lundberg.http;

import com.github.tomakehurst.wiremock.http.Fault;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by stefanlundberg on 15-03-14.
 */
public class HttpServer {

    private static String PATH = "/content";

    public void createServer(int statusCode, String body, String contentType) {
        givenThat(get(urlEqualTo(PATH))
                .willReturn(aResponse()
                        .withStatus(statusCode)
                        .withHeader("Content-Type", contentType)
                        .withBody(body)));
    }

    public void createServerWithFaultResponse(String body, String contentType) {
        givenThat(get(urlEqualTo(PATH))
                .willReturn(aResponse()
                        .withFault(Fault.EMPTY_RESPONSE)
                        .withHeader("Content-Type", contentType)
                        .withBody(body)));
    }
}
