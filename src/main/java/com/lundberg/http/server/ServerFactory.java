package com.lundberg.http.server;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.lundberg.http.server.type.FaultyResponseServer;
import com.lundberg.http.server.type.ResponseTransformerServer;
import com.lundberg.http.server.type.SimpleServer;

/**
 * Created by stefanlundberg on 15-03-16.
 */
public class ServerFactory {

    public static Server build(ServerType type, WireMockConfiguration configuration) {
        Server server = null;

        switch (type) {

            case SIMPLE:
                return new SimpleServer(new WireMockServer());
            case FAULTY:
                return new FaultyResponseServer(new WireMockServer());
            case TRANSFORMER:
                return new ResponseTransformerServer(new WireMockServer(configuration));
            default:
                throw new IllegalArgumentException("Unknown server type:" + type);
        }
    }
}
