import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.lundberg.http.ByteResponseTransformer;
import com.lundberg.http.HttpClient;
import com.lundberg.http.HttpResult;
import com.lundberg.http.HttpServer;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.fluent.Content;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StubbedServerTest {

    /* Resources */
    private static String CONTENT_TYPE = "text/plain";
    private static String CONTENT = "some content";
    private static String PATH = "/content";
    private static String URL = "http://localhost:8080" + PATH;

    private HttpClient client;
    private HttpServer server;

    @Before
    public void setup() { //TODO Make prettier!!!
        //server = new HttpServer(new WireMockServer(new WireMockConfiguration().port(8080)));
        server = new HttpServer(new WireMockServer(new WireMockConfiguration().port(8080).extensions(new ByteResponseTransformer())));
        server.startServer();
        client = new HttpClient(new HttpResult());
    }

    @After
    public void teardown() {
        server.stopServer();
    }

    @Test
    public void check_body_content_when_get() throws Exception {
        server.createServer(200, CONTENT, CONTENT_TYPE);
        String actual = client.getContentAsString((URL));
        assertThat(actual, is(CONTENT));
    }

    @Test
    public void check_content_type_when_get() throws Exception {
        server.createServer(200, CONTENT, CONTENT_TYPE);
        HttpResponse actual = client.getHttpResponse(URL);
        assertThat(actual.getEntity().getContentType().getValue(), is("text/plain"));
    }

    @Test
    public void check_http_status_when_get_OK() throws  Exception {
        server.createServer(200, CONTENT, CONTENT_TYPE);
        HttpResult result = client.getHttpResult(URL);
        assertTrue(result.isSuccessful());
    }

    @Test
    public void check_http_status_when_get_BAD_REQUEST() throws Exception {
        server.createServer(400, CONTENT, CONTENT_TYPE);
        HttpResult result = client.getHttpResult(URL);
        assertFalse(result.isSuccessful());
    }

    @Test(expected = NoHttpResponseException.class)
    public void throw_no_http_response_exception() throws Exception {
        server.createServerWithFaultResponse(CONTENT, "text/plain");
        HttpResult result = client.getHttpResult(URL);
        assertFalse(result.isSuccessful());
    }

    @Test
    public void transform_body_content_to_other_content() throws IOException {
        server.createServerWithResponseTransformer(CONTENT);
        Content content = client.getContent(URL);
        assertEquals(CONTENT, content);
    }
}