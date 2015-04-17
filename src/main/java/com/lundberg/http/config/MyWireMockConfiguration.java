package com.lundberg.http.config;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class MyWireMockConfiguration extends WireMockConfiguration {

    private final String transformerName;

    public MyWireMockConfiguration(String transformerName) {
        super();
        this.transformerName = transformerName;
    }

    public String getTransformerName() {
        return transformerName;
    }
}
