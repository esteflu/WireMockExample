import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class StubedServerTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);

    private HttpClient client = new HttpClient();

    private static String PATH = "/content";

    private static String URL = "http://localhost:8080" + PATH;

    @Before
    public void setup() {
        createStubServer(200, "some content", "text/plain");
    }

    @Test
    public void get_content_as_string_when_get() throws Exception {
        String actual = client.getContentAsString((URL));
        assertThat(actual, is("some content"));
    }

    @Test
    public void get_http_response_when_get() throws Exception{
        HttpResponse actual = client.getHttpResponse(URL);
        assertThat(actual.getStatusLine().getStatusCode(), is(200));
        assertThat(actual.getEntity().getContentType().getValue(), is("text/plain"));
    }

    private void createStubServer(int statusCode, String body, String contentType) {
        stubFor(get(urlEqualTo(PATH))
                .willReturn(aResponse()
                        .withStatus(statusCode)
                        .withHeader("Content-Type", contentType)
                        .withBody(body)));
    }
}