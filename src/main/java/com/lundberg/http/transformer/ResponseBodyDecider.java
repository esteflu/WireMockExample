package com.lundberg.http.transformer;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

public class ResponseBodyDecider extends ResponseTransformer {
    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource fileSource) {
        return null;
    }

    @Override
    public String name() {
        return "responseBodyDecider";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}
