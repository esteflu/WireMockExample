package com.lundberg.http.server;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

/**
 * Created by stefanlundberg on 15-03-16.
 */
public class ServerFactory {

    public static Server build(ServerType type, WireMockConfiguration configuration) {
        Server server = null;

        switch (type) {

            case SIMPLE:
                server = new SimpleServer(new WireMockServer());
                break;
            case FAULTY:
                server = new FaultResponseServer(new WireMockServer());
                break;
            case TRANSFORMER:
                server = new ResponseTransformerServer(new WireMockServer(configuration));
                break;
            default:
                throw new IllegalArgumentException("Unknown server type:" + type);
        }
        return server;

    }
}
