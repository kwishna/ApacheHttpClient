package HttpOne;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JacksonMethods {

	public static void main(String[] args) throws IOException {
		
		ObjectMapper obj = new ObjectMapper();
		System.out.println("String To JsonNode ::: "+obj.readTree("{\"Hi\" : \"Bye\"}").toString());
		
		//Writing Student Object As Json In A File
		Student stu = new Student("Krsna", 16);
		obj.writerWithDefaultPrettyPrinter().writeValue(new File(System.getProperty("user.dir")+"/student.json"), stu);
		
		// Student Object To String
		String stud = obj.writeValueAsString(stu);
		System.out.println("Student Object ::: "+stud);
		
		JsonNode fileJson = obj.readTree(new File(System.getProperty("user.dir")+"/student.json")); // Reading Json File Into JsonNOode
		System.out.println("File Json ::: "+fileJson.toString());
		
		Map<String,String> payload = new HashMap<>();
		payload.put("key1","value1");
		payload.put("key2","value2");
		System.out.println("Map To Json ::: "+obj.writeValueAsString(payload));
		
		System.out.println("String To JsonNode ::: "+obj.readTree("{\"id\": 4, \"title\": \"json-server1\", \"author\": \"typicode\"}"));
		
		String prettyPrint = obj.writerWithDefaultPrettyPrinter().writeValueAsString(obj.readTree("{\"id\": 4, \"title\": \"json-server1\", \"author\": \"typicode\"}"));
		System.out.println("Pretty Format Json ::: "+prettyPrint);
		
		JsonNode node = obj.readTree("{\"id\": 4, \"title\": \"json-server1\", \"author\": \"typicode\"}");
		System.out.println("Finding 'title' Value From JsonNode ::: "+node.path("title").asText());
		
		JsonNode node1 = obj.readTree(new File(System.getProperty("user.dir")+"/JsonFile.json"));
		System.out.println("Finding 'store' Value From JsonNode ::: "+node1.path("store").asText());
		
		Map<String, String> stude = obj.convertValue(stu, Map.class);		
		System.out.println("Object To Map ::: "+stude);	

		String jsonInString = obj.writerWithDefaultPrettyPrinter().writeValueAsString(stu);
		obj.configure(SerializationFeature.INDENT_OUTPUT, true);
		obj.writeValue(new File(stu.getAge()+"_employee.json"), stu); // Java Object To Json : 'Marshelling'
		
		Student stuBack = obj.readValue(new File(stu.getAge()+"_employee.json"), Student.class); // Json To Java Object : 'Unmarselling'
		System.out.println("Student Object From File ::: "+stuBack.getName()+" ::: "+stuBack.getAge());
	}
}
