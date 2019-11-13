package HttpOne;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
/**
 * 
 * @author AB D
 *
 * Using Apache HttpClients, HttpCore Jar Files For API Communications.
 * 
 * http://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html - Http Client
 * https://hc.apache.org/httpcomponents-core-dev/tutorial/html/fundamentals.html - Http Core
 * 
 */
public class ApacheHttpAPI {
	
	public CloseableHttpResponse sendGet(String url) throws URISyntaxException, ClientProtocolException, IOException {
		
		CloseableHttpClient client = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(); // Or, HttpGet g = new HttpGet(url); // HttpRequestBase Is Parent Class
		httpGet.setURI(new URI(url));
		httpGet.addHeader("accept", "application/json");
		
		/**
		 * HttpGet httpget = new HttpGet("http://www.google.com/search?hl=en&q=httpclient&btnG=Google+Search&aq=f&oq=");
		 * Or,
		 *	List<NameValuePair> paramlist = new ArrayList<>();
			paramlist.add(new BasicNameValuePair("param1", "value1"));
			paramlist.add(new BasicNameValuePair("param2", "value2"));
		
			URIBuilder builder = new URIBuilder();
		  	URI uri = builder
			.setScheme("http")
			.setHost("www.google.com")
        	.setPath("/search")
        	.setParameter("q", "httpclient")
        	.setParameter("btnG", "Google Search")
        	.setParameter("aq", "f")
        	.setParameter("oq", "")
        	.addParameters(paramlist) // Adds : param1=value1&param2=value2 In URL
        	.build();
         *
		 */
		
		CloseableHttpResponse  response = client.execute(httpGet);
		
		return response;
	}
	
	// Using Response Handler
	public String getResponse(String url) throws ClientProtocolException, IOException, URISyntaxException {
		
		// Create a custom response handler
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            @Override
            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
        
        CloseableHttpClient client = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(); // Or, HttpGet g = new HttpGet(url); // HttpRequestBase Is Parent Class
		httpGet.setURI(new URI(url));
		httpGet.addHeader("accept", "application/json");
        
		String reponse = client.execute(httpGet, responseHandler); // Getting Response As String Using Response Handler
		
		client.close();
		
        return reponse;       
	}
	
	// Getting All Headers
	public void printHeaders(CloseableHttpResponse  response) {
		
		Header[] header = response.getAllHeaders(); // Header header = new BasicHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
		Arrays.asList(header).forEach(a->{
				System.out.println(a.getName() +" ::: "+a.getValue());
		});	
	}
	
	// Getting Response Code
	public int statusCode(CloseableHttpResponse  response) {
		
		return response != null ? response.getStatusLine().getStatusCode() : -1;
	}
	
	// Getting Response Content
	public String printBody(CloseableHttpResponse  response) throws ParseException, IOException {
		
		HttpEntity entity = response.getEntity();
		String responseContent = EntityUtils.toString(entity);
		
		return responseContent;
	}
	
	// Converting To Pretty JSON
	public String prettyJson(String string) {
		
		return new GsonBuilder()
		.setPrettyPrinting()
		.create()
		.toJson(
				new JsonParser().parse(string)
				);
	}
	
	// Pretty Json Using Jackson
	public String prettyJsonUsingJackson(String string) throws JsonProcessingException, IOException {
		
		ObjectMapper obj = new ObjectMapper();
		String prettyPrint = obj.writerWithDefaultPrettyPrinter().writeValueAsString(obj.readTree(string));
		return prettyPrint;
	}
	
	// Adding Header As Map In GET Request
	public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException{
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); //http get request
		
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httpget.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closebaleHttpResponse =  httpClient.execute(httpget); //hit the GET URL
		return closebaleHttpResponse;
	}
	
	// Getting Value Of From JSON Content Body
	public String getValueByJPath(JSONObject responsejson, String jpath){ // Convert JSON String To JSON Object Using "JSONObject jsonObject = new JSONObject(jsonString);"
		Object obj = responsejson;
		for(String s : jpath.split("/")) 
			if(!s.isEmpty()) 
				if(!(s.contains("[") || s.contains("]")))
					obj = ((JSONObject) obj).get(s);
				else if(s.contains("[") || s.contains("]"))
					obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
		return obj.toString();
	}
	
	// HEAD METHOD : DOESN'T CONTAIN BODY.
	public void headMethod() throws URISyntaxException, ClientProtocolException, IOException {
		
		CloseableHttpClient client = HttpClients.createDefault();
		
		HttpHead head = new HttpHead();
		head.setURI(new URI("http://localhost:3000/posts"));
		head.addHeader("accept", "application/json");
		
		CloseableHttpResponse response = client.execute(head);
		System.out.println(response);
	}
	
	public void postData() {
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("http://www.yoursite.com/user");

	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("id", "12345"));
	        nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        ResponseHandler<String> responseHandler=new BasicResponseHandler();
	        String responseBody = httpclient.execute(httppost, responseHandler);
	        JSONObject response=new JSONObject(responseBody);
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	} 
	
	

	public static void main(String[] args) throws URISyntaxException, ClientProtocolException, IOException {
		
		ApacheHttpAPI http = new ApacheHttpAPI();
		CloseableHttpResponse res = http.sendGet("https://reqres.in/api/users/2");
		
		http.printHeaders(res);
		String body = http.printBody(res);
		System.out.println(http.prettyJsonUsingJackson(body)); // Inside Body Method. We Are Reading Response 1st Time
		
//		System.out.println(EntityUtils.toString(res.getEntity(), "UTF-8")); // You Can'T Read The Response Twice. "Stream Closed" Error	
//		System.out.println(http.prettyJson(EntityUtils.toString(res.getEntity(), "UTF-8")));	// You Can'T Read The Response Twice. The response stream has no more data to read.
		
		// Get Request - json-server Start With Node
//		System.out.println(http.prettyJson(EntityUtils.toString(http.sendGet("http://localhost:3000/posts/1").getEntity(), "UTF-8")));		
				
//		// Post Request
//		CloseableHttpClient c = HttpClients.createDefault();
//		HttpPost post = new HttpPost("http://localhost:3000/posts");
//		StringEntity e = new StringEntity("{\"id\": 4, \"title\": \"json-server1\", \"author\": \"typicode\"}", ContentType.APPLICATION_JSON);
//		post.addHeader("Content-Type", "application/json");
//		post.setEntity(e); // HttpEntity entity = new StringEntity(body);
		
		/** If We Have Any Class, We Can Do Marshelling & De-Marshelling. Means Converting An Class Object To JSON And Vice Versa.
		 * Using Jackson API.
		 * 
		 * ObjectMapper obj = new ObjectMapper();
		 * Student stu = new Student("Krsna", 16);
		 * obj.writerWithDefaultPrettyPrinter().writeValue(new File(System.getProperty("user.dir")+"/student.json"), stu); // Creates File 
		 * 
		 * String stud = obj.writeValueAsString(stu); // Student Object As String,
		 * JsonNode node1 = obj.readTree(new File(System.getProperty("user.dir")+"/JsonFile.json")); // Reading Json File Into JsonNode
		 * 
		 * DocumentContext doc = JsonPath.parse(new File(System.getProperty("user.dir")+"/JsonFile.json")); // JsonPath Jar
		 * System.out.println(doc.jsonString()); // Reading File Into JSON String
		 * 
		 */
		
		// Using File For Entity
		ObjectMapper obj = new ObjectMapper();
		StringEntity e1 = new StringEntity(obj.readTree(new File(System.getProperty("user.dir")+"/jsonFile.json")).toString());
		System.out.println("Entity e1 ::: "+EntityUtils.toString(e1));
		
//		CloseableHttpClient c2 = HttpClients.createDefault();		
//		CloseableHttpResponse cl = c2.execute(post);
//		System.out.println(cl.getStatusLine().getStatusCode());
//	//	cl.close();
		
//		// Delete Request
//		HttpDelete del = new HttpDelete("http://localhost:3000/posts/1");
//		del.addHeader("Content-Type", "application/json");
//		System.out.println(c2.execute(del).getStatusLine().getStatusCode());
	}
}
