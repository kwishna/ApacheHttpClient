package HttpOne;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

/**
 * 
 * @author AB D
 *
 * https://github.com/json-path/JsonPath#path-examples
 * 
 * Best For JSON Parsing 
 */
public class JsonPathMethods {

	public static void main(String[] args) throws IOException {
		
		DocumentContext doc = JsonPath.parse(new File(System.getProperty("user.dir")+"/JsonFile.json"));
		System.out.println(doc.jsonString());
		
		JsonPath.parse("{\"id\": 4, \"title\": \"json-server1\", \"author\": \"typicode\"}");
		
//		System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(new ObjectMapper().readTree(doc.jsonString())));
		
		String value = JsonPath.read(doc.jsonString(), "$.store.book[0].author");
		System.out.println(value);
		
		String value1 = JsonPath.read("{\"store\":{\"book\":[{\"category\":\"reference\",\"author\":\"Nigel Rees\",\"title\":\"Sayings of the Century\",\"price\":8.95},{\"category\":\"fiction\",\"author\":\"Evelyn Waugh\",\"title\":\"Sword of Honour\",\"price\":12.99},{\"category\":\"fiction\",\"author\":\"Herman Melville\",\"title\":\"Moby Dick\",\"isbn\":\"0-553-21311-3\",\"price\":8.99},{\"category\":\"fiction\",\"author\":\"J. R. R. Tolkien\",\"title\":\"The Lord of the Rings\",\"isbn\":\"0-395-19395-8\",\"price\":22.99}],\"bicycle\":{\"color\":\"red\",\"price\":19.95}}}",
				"$.store.book[0].author");
		System.out.println(value1);
		
		System.out.println(JsonPath.read(doc.jsonString(), "$.store.book[0]").toString());
		
		String json = "{\"date_as_long\" : 1411455611975}";

		Date date = JsonPath.parse(json).read("$['date_as_long']", Date.class);
		System.out.println(date);
		
	}
}
