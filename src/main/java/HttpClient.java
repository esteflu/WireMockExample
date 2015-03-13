import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

/**
 * Created by stefanlundberg on 15-03-11.
 */
public class HttpClient {

    public String getContentAsString(String url) throws IOException {
        return Request.Get(url).execute().returnContent().asString();
    }

    public HttpResponse getHttpResponse(String url) throws IOException {
        return Request.Get(url).execute().returnResponse();
    }
}
