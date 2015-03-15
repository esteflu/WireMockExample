package com.lundberg.http;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by stefanlundberg on 15-03-14.
 */
public class HttpServer {

    private static String PATH = "/content";
    private WireMockServer mock;

    public HttpServer(WireMockServer mock) {
        this.mock = mock;
    }

    public void createServer(int statusCode, String body, String contentType) {
        mock.givenThat(get(urlEqualTo(PATH))
                .willReturn(aResponse()
                        .withStatus(statusCode)
                        .withHeader("Content-Type", contentType)
                        .withBody(body)));
    }

    public void createServerWithFaultResponse(String body, String contentType) {
        mock.givenThat(get(urlEqualTo(PATH))
                .willReturn(aResponse()
                        .withFault(Fault.EMPTY_RESPONSE)
                        .withHeader("Content-Type", contentType)
                        .withBody(body)));
    }

    public void createServerWithResponseTransformer(String body) {

        mock.givenThat(get(urlEqualTo(PATH))
                .willReturn(aResponse()
                        .withBody(body)
                        .withTransformers("byteResponseTransformer")));
    }

    public void startServer() {
        mock.start();
    }

    public void stopServer() {
        mock.stop();
    }
}
