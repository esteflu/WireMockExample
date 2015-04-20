import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.lundberg.http.client.HttpClient;
import com.lundberg.http.config.MyWireMockConfiguration;
import com.lundberg.http.result.HttpResult;
import com.lundberg.http.server.Server;
import com.lundberg.http.server.ServerFactory;
import com.lundberg.http.server.ServerType;
import com.lundberg.http.transformer.ResponseBodyDecider;
import com.lundberg.http.transformer.ResponseBodyReplacer;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.fluent.Content;
import org.junit.After;
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

    private String contentType;
    private String content;
    private HttpClient client = null;
    private Server server;

    @Before
    public void setup() {
        client = new HttpClient(new HttpResult());
        server = null;
        setContent(CONTENT);
        setContentType(CONTENT_TYPE);
    }

    @After
    public void stopServer() {
        server.stopServer();
    }

    @Test
    public void check_body_content_when_get() throws Exception {
        setUpServer(200, SIMPLE, null);

        String actual = client.getContentAsString((URL));
        assertThat(actual, is(CONTENT));
    }

    @Test
    public void check_content_type_when_get() throws Exception {
        setUpServer(200, SIMPLE, null);

        HttpResponse actual = client.getHttpResponse(URL);
        assertThat(actual.getEntity().getContentType().getValue(), is(CONTENT_TYPE));
    }

    @Test
    public void check_http_status_when_get_OK() throws  Exception {
        setUpServer(200, SIMPLE, null);

        HttpResult result = client.getHttpResult(URL);
        assertTrue(result.isSuccessful());
    }

    @Test
    public void check_http_status_when_get_BAD_REQUEST() throws Exception {
        setUpServer(400, SIMPLE, null);

        HttpResult result = client.getHttpResult(URL);
        assertFalse(result.isSuccessful());

    }

    @Test(expected = NoHttpResponseException.class)
    public void throw_no_http_response_exception() throws Exception {
        setUpServer(0, FAULTY, null);

        HttpResult result = client.getHttpResult(URL);
        assertFalse(result.isSuccessful());
    }

    @Test
    public void transform_body_content_to_other_content() throws IOException {
        setUpServer(200, TRANSFORMER, new MyWireMockConfiguration("responseBodyReplacer").extensions(ResponseBodyReplacer.class));

        Content otherContent = client.getContent(URL);
        assertNotEquals(CONTENT, otherContent.toString());
    }

    @Test
    public void transform_body_content_depending_on_request_headers() throws IOException {
        setContentType("not text/plain type");
        setUpServer(200, TRANSFORMER, new MyWireMockConfiguration("responseBodyDecider").extensions(ResponseBodyDecider.class));
        String actual = client.getContentAsString(URL);
        assertEquals("body for other content type", actual);
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private void setUpServer(int statusCode, ServerType type, WireMockConfiguration extendedConfig) {
        server = ServerFactory.build(type, extendedConfig);
        server.configure(statusCode, getContent(), getContentType());
        server.startServer();
    }
}