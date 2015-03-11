import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class HttpGetTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);

    private HttpGet httpGet;

    private static String URL = "http://localhost:8080/content";

    @Before
    public void init() {
        httpGet = new HttpGet();
        stubFor(get(urlEqualTo("/content"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("some content")));
    }

    @Test
    public void get_body_when_get() throws Exception {
        String actual = httpGet.getAsString((URL));
        String expected = "some content";
        assertThat(actual, is(expected));
    }

    @Test
    public void get_http_response_when_get() throws Exception{
        HttpResponse actual = httpGet.getHttpResponse(URL);
        assertThat(actual.getStatusLine().getStatusCode(), is(200));
        assertThat(actual.getEntity().getContentType().getValue(), is("text/plain"));
    }
}