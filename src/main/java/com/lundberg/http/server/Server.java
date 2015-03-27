package com.lundberg.http.server;

import com.github.tomakehurst.wiremock.WireMockServer;

/**
 * Created by stefanlundberg on 15-03-16.
 */
public abstract class Server {

    protected static String PATH = "/content";
    private WireMockServer mock;
    private ServerType type;

    public Server(WireMockServer mock, ServerType type) {
        this.mock = mock;
        this.type = type;
    }

    public abstract void configure(int statusCode, String body, String contentType);

    public WireMockServer getMock() {
        return mock;
    }

    public ServerType getType() {
        return type;
    }

    public void startServer() {
        mock.start();
    }

    public void stopServer() {
        mock.stop();
    }

    public boolean isServerStarted() {
        return mock.isRunning();
    }
}
