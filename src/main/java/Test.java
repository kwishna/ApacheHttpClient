import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Test {

	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
		
		HttpClient client = HttpClient.newBuilder().build();
//		HttpRequest req = HttpRequest.newBuilder(new URI("http://localhost:3000/posts/1")).header("Content-Type", "application/json").PUT(BodyPublishers.ofString("{ \"id\": 1, \"title\": \"json-server2\", \"author\": \"typicode2\" }")).build();
		
		HttpRequest req = HttpRequest.newBuilder(new URI("http://localhost:3000/posts"))
				.header("Content-Type", "application/json")
				.POST(BodyPublishers.ofString("{ \"id\": 6 }")).build();		
		
		HttpResponse<String> res = client.send(req, BodyHandlers.ofString());
		System.out.println("Body ::: "+res.body()+" Response Code ::: "+res.statusCode());
		
	}

}
