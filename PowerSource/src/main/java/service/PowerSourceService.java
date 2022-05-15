package service;

import model.PowerSource;
import service.InterServiceCommunication;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;


@Path("/PowerSource")
public class PowerSourceService {
	PowerSource ps = new PowerSource();
	
	//Insert
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertItem(@FormParam("Name") String Name,
								@FormParam("Address") String Address,
								@FormParam("Province") String Province,
								@FormParam("Type") String Type,
								@FormParam("PowerGenerated") String PowerGenerated,
								@FormParam("Maintenance_Day") String Maintenance_Day,
								@FormParam("Head_Engineer") String Head_Engineer)
	{
		String output = ps.insertPowerSource(Name,Address,Province,Type,PowerGenerated,Maintenance_Day,Head_Engineer);
		return output;
	}
	
	//Read everything
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPowerSource() {
		return ps.readPowerSource();
	}
	
	//Update
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItem(String itemData) {
		//Covert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		
		//Read the values from the JSON Object
		String ID = itemObject.get("ID").getAsString();
		String Name = itemObject.get("Name").getAsString();
		String Address = itemObject.get("Address").getAsString();
		String Province = itemObject.get("Province").getAsString();
		String Type = itemObject.get("Type").getAsString();
		String PowerGenerated = itemObject.get("PowerGenerated").getAsString();
		String Maintenance_Day = itemObject.get("Maintenance_Day").getAsString();
		String Head_Engineer = itemObject.get("Head_Engineer").getAsString();
		
		String output = ps.updatePowerSource(ID,Name,Address,Province,Type,PowerGenerated,Maintenance_Day,Head_Engineer);
		return output;
	}
	
	//Delete
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePowerSource(String powerSourceData) {
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(powerSourceData,"",Parser.xmlParser());
		
		//Read the value from the element <itemID>
		String ID = doc.select("ID").text();
		String output = ps.deletePowerSource(ID);
		return output;
	}
	/*Inter-service communication between the employee*/
	@GET
	@Path("/{eng_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String readEmpSource(@PathParam("eng_id") String id) {
		
		JsonObject result = null;
		
		result=ps.readSourceEmp(id);
		return result.toString();
		
	}
	
	//Interservice communication with employee
	@GET
	@Path("/Power/{nic}")
	@Produces(MediaType.APPLICATION_JSON)
	public String readSource(@PathParam("nic") String nic)
	 {
		 
		 return (new InterServiceCommunication().EmployeeDetails("/" + nic)).toString();
		 //return emp.rEmp(br);
	}
}
