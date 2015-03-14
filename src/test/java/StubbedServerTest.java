import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.lundberg.http.HttpClient;
import com.lundberg.http.HttpResult;
import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class StubbedServerTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);

    private HttpClient client;

    private static String PATH = "/content";

    private static String URL = "http://localhost:8080" + PATH;

    @Before
    public void setup() {
        client = new HttpClient(new HttpResult());
    }

    @Test
    public void check_body_content_when_get() throws Exception {
        createSimpleServer(200, "some content", "text/plain");
        String actual = client.getContentAsString((URL));
        assertThat(actual, is("some content"));
    }

    @Test
    public void check_content_type_when_get() throws Exception {
        createSimpleServer(200, "some content", "text/plain");
        HttpResponse actual = client.getHttpResponse(URL);
        assertThat(actual.getEntity().getContentType().getValue(), is("text/plain"));
    }

    @Test
    public void check_http_status_when_get_OK() throws  Exception {
        createSimpleServer(200, "some content", "text/plain");
        HttpResult result = client.getHttpResult(URL);
        assertTrue(result.isSuccessful());
    }

    @Test
    public void check_http_status_when_get_BAD_REQUEST() throws Exception {
        createSimpleServer(400, "some content", "text/plain");
        HttpResult result = client.getHttpResult(URL);
        assertFalse(result.isSuccessful());
    }

    private void createSimpleServer(int statusCode, String body, String contentType) {
        stubFor(get(urlEqualTo(PATH))
                .willReturn(aResponse()
                        .withStatus(statusCode)
                        .withHeader("Content-Type", contentType)
                        .   withBody(body)));
    }


}