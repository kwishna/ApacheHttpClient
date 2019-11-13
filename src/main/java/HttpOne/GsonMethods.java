package HttpOne;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonMethods {

	public static void main(String[] args) throws IOException {
		
		String jsonString = "{\"name\":\"Mahesh\", \"age\":21}";
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting(); 
	      
	    Gson gson = builder.create();
	    Student stu = gson.fromJson(jsonString, Student.class);
	    System.out.println(stu);
	    System.out.println(gson.toJson(stu));
	    
//	    FileWriter writer = new FileWriter("studentA.json");   
//	    writer.write(gson.toJson(stu));   
//	    writer.close(); 
	    
	    JsonParser parser = new JsonParser(); 
	    String jsonStr = "{\"name\":\"Mahesh Kumar\", \"age\":21,\"verified\":false,\"marks\": [100,90,85]}"; 
	    JsonElement rootNode = parser.parse(jsonStr);   
	    JsonObject details = rootNode.getAsJsonObject(); 
	    JsonElement nameNode = details.get("name"); 
	    System.out.println("Name: " +nameNode.getAsString()); 
	    
	    Map<String,String> payload = new HashMap<>();
		payload.put("key1","value1");
		payload.put("key2","value2");
	    Gson gson1 = new Gson(); 
	    String json = gson1.toJson(payload); 
	    System.out.println(json);
	      
	}
}
