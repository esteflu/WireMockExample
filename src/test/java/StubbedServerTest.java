import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.lundberg.http.HttpClient;
import com.lundberg.http.HttpResult;
import com.lundberg.http.HttpServer;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StubbedServerTest {

    private static String CONTENT_TYPE = "text/plain";
    private static String CONTENT = "some content";
    private static String PATH = "/content";
    private static String URL = "http://localhost:8080" + PATH;
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);
    private HttpClient client;

    @Before
    public void setup() {
        client = new HttpClient(new HttpResult());
    }

    @Test
    public void check_body_content_when_get() throws Exception {
        createSimpleServer(200);
        String actual = client.getContentAsString((URL));
        assertThat(actual, is(CONTENT));
    }

    @Test
    public void check_content_type_when_get() throws Exception {
        createSimpleServer(200);
        HttpResponse actual = client.getHttpResponse(URL);
        assertThat(actual.getEntity().getContentType().getValue(), is("text/plain"));
    }

    @Test
    public void check_http_status_when_get_OK() throws  Exception {
        createSimpleServer(200);
        HttpResult result = client.getHttpResult(URL);
        assertTrue(result.isSuccessful());
    }

    @Test
    public void check_http_status_when_get_BAD_REQUEST() throws Exception {
        createSimpleServer(400);
        HttpResult result = client.getHttpResult(URL);
        assertFalse(result.isSuccessful());
    }

    @Test(expected = NoHttpResponseException.class)
    public void throw_no_http_response_exception() throws Exception {
        new HttpServer().createServerWithFaultResponse("some content", "text/plain");
        HttpResult result = client.getHttpResult(URL);
        assertFalse(result.isSuccessful());
    }

    private void createSimpleServer(int statusCode) {
        new HttpServer().createServer(statusCode, CONTENT, CONTENT_TYPE);
    }
}