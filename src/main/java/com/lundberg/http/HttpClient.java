package com.lundberg.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import java.io.IOException;

/**
 * Created by stefanlundberg on 15-03-11.
 */

public class HttpClient {

    private HttpResult result;

    public HttpClient(HttpResult result) {
        this.result = result;
    }

    public String getContentAsString(String url) throws IOException {
        return doExecute(url).returnContent().asString();
    }

    public HttpResponse getHttpResponse(String url) throws IOException {
        return doExecute(url).returnResponse();
    }

    private Response doExecute(String url) throws IOException {
        return Request.Get(url).execute();
    }

    public HttpResult getHttpResult(String url) throws IOException {
        int statusCode = this.getHttpResponse(url).getStatusLine().getStatusCode();
        result.checkStatusCode(statusCode);
        return result;
    }

    public Content getContent(String url) throws IOException {
        return doExecute(url).returnContent();
    }
}
