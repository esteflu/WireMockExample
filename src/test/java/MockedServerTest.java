import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.lundberg.http.HttpResult;
import com.lundberg.http.ResponseTransformerImpl;
import com.lundberg.http.client.HttpClient;
import com.lundberg.http.server.HttpServer;
import com.lundberg.http.server.Server;
import com.lundberg.http.server.ServerFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.fluent.Content;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.lundberg.http.server.ServerType.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MockedServerTest {

    /* Resources */
    private static String CONTENT_TYPE = "text/plain";
    private static String CONTENT = "some content";
    private static String PATH = "/content";
    private static String URL = "http://localhost:8080" + PATH;

    private HttpClient client;
    private HttpServer server;

    @Before
    public void setup() {
        client = new HttpClient(new HttpResult());
    }

    @Test //TODO fix address already in use
    public void check_body_content_when_get() throws Exception {
        Server server = ServerFactory.build(SIMPLE, null);
        server.setServerType(200, CONTENT, CONTENT_TYPE);
        server.startServer();
        String actual = client.getContentAsString((URL));
        assertThat(actual, is(CONTENT));
        server.stopServer();
    }

    @Test
    public void check_content_type_when_get() throws Exception {
        Server server = ServerFactory.build(SIMPLE, null);
        server.setServerType(200, CONTENT, CONTENT_TYPE);
        server.startServer();
        HttpResponse actual = client.getHttpResponse(URL);
        assertThat(actual.getEntity().getContentType().getValue(), is(CONTENT_TYPE));
        server.stopServer();
    }

    @Test
    public void check_http_status_when_get_OK() throws  Exception {
        Server server = ServerFactory.build(SIMPLE, null);
        server.setServerType(200, CONTENT, CONTENT_TYPE);
        server.startServer();
        HttpResult result = client.getHttpResult(URL);
        assertTrue(result.isSuccessful());
        server.stopServer();
    }

    @Test
    public void check_http_status_when_get_BAD_REQUEST() throws Exception {
        Server server = ServerFactory.build(SIMPLE, null);
        server.setServerType(400, CONTENT, CONTENT_TYPE);
        server.startServer();
        HttpResult result = client.getHttpResult(URL);
        assertFalse(result.isSuccessful());
        server.stopServer();
    }

    @Test(expected = NoHttpResponseException.class)
    public void throw_no_http_response_exception() throws Exception {
        Server server = ServerFactory.build(FAULTY, null);
        server.setServerType(0, CONTENT, CONTENT_TYPE);
        server.startServer();
        HttpResult result = client.getHttpResult(URL);
        assertFalse(result.isSuccessful());
        server.stopServer();
    }

    @Test
    public void transform_body_content_to_other_content() throws IOException {
        Server server = ServerFactory.build(TRANSFORMER, new WireMockConfiguration().extensions(new ResponseTransformerImpl()));
        server.setServerType(0, CONTENT, null);
        server.startServer();
        Content other = client.getContent(URL);
        //TODO fix assetion between CONTENT and other
        server.stopServer();
    }
}