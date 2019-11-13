package HttpOne;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class TestApacheHttp {

	public static void main(String[] args) throws ClientProtocolException, IOException, URISyntaxException {
		
		CloseableHttpClient client = HttpClients.createDefault();
		
		HttpHead head = new HttpHead();
		head.setURI(new URI("http://localhost:3000/posts"));
		head.addHeader("accept", "application/json");
		
		HttpOptions options = new HttpOptions("http://localhost:3000/posts");
		
		CloseableHttpResponse response = client.execute(options);
		System.out.println(response);
	}

}
