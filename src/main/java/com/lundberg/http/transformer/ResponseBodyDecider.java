package com.lundberg.http.transformer;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

import java.io.Serializable;

public class ResponseBodyDecider extends ResponseTransformer {
    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource fileSource) {
        ResponseDefinition resp = ResponseDefinitionBuilder.like(responseDefinition).build();
        if (containsHeaderValue(resp, buildHeader())) {
            return new ResponseDefinitionBuilder().withBody("body for text/plain content type").build();
        }
        return  new ResponseDefinitionBuilder().withBody("body for other content type").build();
    }

    @Override
    public String name() {
        return "responseBodyDecider";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }

    private boolean containsHeaderValue(ResponseDefinition responseDefinition, Header header) {
        return responseDefinition
                .getHeaders()
                .getHeader(header.getKey())
                .firstValue().equals(header.getValue());
    }

    private Header buildHeader() {
        return new Header("Content-Type", "text/plain");
    }

    private class Header {
        private final String key;
        private final String value;

        public Header(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}
