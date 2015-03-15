package com.lundberg.http;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

/**
 * Created by stefanlundberg on 15-03-15.
 */
public class ByteResponseTransformer extends ResponseTransformer {
    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource fileSource) {
        return new ResponseDefinitionBuilder().like(responseDefinition)
                .but()
                .withBody(responseDefinition.getBody().replace("some content", "other content"))
                .build();
    }

    @Override
    public String name() {
        return "byteResponseTransformer";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}
