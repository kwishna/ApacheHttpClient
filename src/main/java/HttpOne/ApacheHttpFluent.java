package HttpOne;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;

public class ApacheHttpFluent {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		Response res = Request.Get("http://localhost:3000/posts")
				.version(HttpVersion.HTTP_1_1)
				.addHeader("Content-Type", "application/json")		
//				.body(new StringEntity(""))
//			    .viaProxy(new HttpHost("myproxy", 8080)).bodyForm(Form.form().add("username", "vip").add("password", "secret").build())
				.execute();																		
		
		System.out.println("Response ::: "+res.returnContent().asString(Charset.forName("UTF-8")));
//		res.saveContent(new File(System.getProperty("user.dir")+"/fluent.json"));
	
//		***********************************************************************************************************************		
		
// 		Through Executer We Can Set The Authentication Details For Re-use
		Executor executor = Executor.newInstance();
//		        .auth(new HttpHost("somehost"), "username", "password")
//		        .auth(new HttpHost("myproxy", 8080), "username", "password")
//		        .authPreemptive(new HttpHost("myproxy", 8080));
		
		executor.execute(Request.Get("http://localhost:3000/posts"))
		        .returnContent().asString();
		
		executor.execute(Request.Post("http://localhost:3000/posts")
		        .useExpectContinue()
		        .bodyString("{\r\n" + 
		        		"  \"id\": 6,\r\n" + 
		        		"  \"title\": \"json-server2\",\r\n" + 
		        		"  \"author\": \"typicode2\"\r\n" + 
		        		"}", ContentType.APPLICATION_JSON))
		        .returnContent().asString();

//		Delete Post 6
		Response res2 = executor
				.execute(Request.Delete("http://localhost:3000/posts/6").addHeader("Content-Type", "application/json"));
		System.out.println("Delete Post 6 ::: "+res2.returnResponse().getStatusLine().getStatusCode());
		
//		Delete Post 5.		
		Response res3 = Request.Delete("http://localhost:3000/posts/5")
				.addHeader("Content-Type", "application/json")
				.execute();
		System.out.println("Delete Post 5 ::: "+res3.returnResponse().getStatusLine().getStatusCode());
		
	}
}
