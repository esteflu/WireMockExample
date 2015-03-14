package com.lundberg.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by stefanlundberg on 15-03-11.
 */

public class HttpClient {

    private HttpResult httpResult = new HttpResult(); //TODO add DI with JSR330

    public String getContentAsString(String url) throws IOException {
        return Request.Get(url).execute().returnContent().asString();
    }

    public HttpResponse getHttpResponse(String url) throws IOException {
        return Request.Get(url).execute().returnResponse();
    }

    public HttpResult getHttpResult(String url) throws IOException {
        int statusCode = Request.Get(url).execute().returnResponse().getStatusLine().getStatusCode();
        httpResult.checkStatusCode(statusCode);
        return httpResult;
    }
}
