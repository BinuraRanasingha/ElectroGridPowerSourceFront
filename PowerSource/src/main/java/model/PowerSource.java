package model;

import util.DB_Connector;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.sql.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;


public class PowerSource {
	
	private static Connection con = null;
	
	//insert 
	public String insertPowerSource(String pname, String paddress, String province, String type, String powergenerated, String maint_day,String head) {
		String output = "";
		
		try {
				con = DB_Connector.connect();
			
			if(con == null)
				return "Database connection failed for inserting data";
			
			// create a prepared statement
			String query = " insert into powersource (`ID`,`Name`,`Address`,`Province`,`Type`,`PowerGenerated`,`Maintenance_Day`,`Head_Engineer`)"
					 + " values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1,0);
			preparedStmt.setString(2,pname);
			preparedStmt.setString(3,paddress);
			preparedStmt.setString(4,province);
			preparedStmt.setString(5,type);
			preparedStmt.setString(6,powergenerated);
			preparedStmt.setString(7,maint_day);
			preparedStmt.setString(8,head);
			
			// execute the statement
			preparedStmt.executeUpdate();
			con.close();
			String powerSource = readPowerSource();
			output = "{\"status\":\"success\", \"data\": \"" + powerSource + "\"}";
		}
		
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the power source.\"}";
			System.err.println(e.getMessage());
			
		}
		
		return output;
	}
	
	//Read all the power sources
	public String readPowerSource()
	{
		String output = "";
		try
		{
			con = DB_Connector.connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Power Source Name</th> <th>Address</th><th>Province</th><th>Power Source Type</th><th>Power Generated</th><th>Maintenance Day</th><th>Employee ID of head Engineerr</th>"
			+"<th>Update</th><th>Remove</th></tr>";
			String query = "select * from powersource";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next())
			{
				String ID = Integer.toString(rs.getInt("ID"));
				String Name = rs.getString("Name");
				String Address = rs.getString("Address");
				String Province = rs.getString("Province");
				String Type = rs.getString("Type");
				String PowerGenerated = Integer.toString(rs.getInt("PowerGenerated"));
				String Maintenance_Day = rs.getString("Maintenance_Day");
				String Head_Engineer = Integer.toString(rs.getInt("Head_Engineer"));
				
				// Add into the html table
				output += "<tr><td><input id='hidPowerSourceIDUpdate' name='hidPowerSourceIDUpdate' type='hidden' value='" 
				+ ID + "'>" + Name + "</td>";
				output += "<td>" + Address + "</td>";
				output += "<td>" + Province + "</td>";
				output += "<td>" + Type + "</td>";
				output += "<td>" + PowerGenerated + "</td>";
				output += "<td>" + Maintenance_Day + "</td>";
				output += "<td>" + Head_Engineer + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class=' btnUpdate btn btn-secondary'></td>"
				+"<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-powersource='"+ID+"'></td></tr>";
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		}
		
			catch (Exception e)
			{
				output = "Error while reading the power source details.";
				System.err.println(e.getMessage());
			}
			return output;
	}
	
	//Update
	public String updatePowerSource(String ID, String name, String address, String province, String type, String powerGen, String maint_day,String head)
	{
		String output = "";
		
		try
		{
			con = DB_Connector.connect();
			if (con == null)
				return "Database connection failed for updating data."; 
			
			// create a prepared statement
			String query = "UPDATE powersource SET Name=?,Address=?,Province=?,Type=?,PowerGenerated=?,Maintenance_Day=?,Head_Engineer=? WHERE ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, address);
			preparedStmt.setString(3, province );
			preparedStmt.setString(4, type);
			preparedStmt.setString(5,powerGen);
			preparedStmt.setString(6, maint_day);
			preparedStmt.setString(7, head);
			preparedStmt.setInt(8, Integer.parseInt(ID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			String powerSource = readPowerSource();
			output = "{\"status\":\"success\", \"data\": \"" + powerSource + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while updating the power source.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	//Delete
	public String deletePowerSource(String ID)
	{
		String output = "";
		
		try
		{
			con = DB_Connector.connect();
			if (con == null)
				return "Database connection failed for deleting data."; 
			
			// create a prepared statement
			
		String query = "delete from powersource where ID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		// binding values
		preparedStmt.setInt(1, Integer.parseInt(ID));
		
		// execute the statement
		preparedStmt.execute();
		con.close();
		String powersource = readPowerSource();
		output = "{\"status\":\"success\", \"data\": \"" + powersource + "\"}";
	}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the power source.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	/**Reading Source by the Employee Id**/
	public JsonObject readSourceEmp(String id)
	{
		JsonObject output = null;
		
		try
		{
			con = DB_Connector.connect();
			if (con == null) {
				output=new JsonObject();
				output.addProperty("MESSAGE", "Database connection failed for reading data.");
				//return "Database connection failed for reading data.";
			}
			//
			String query = "select * from powersource where Head_Engineer='"+id+"'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next())
			{
				JsonObject dbObject = new JsonObject();
				dbObject.addProperty("name", rs.getString("Name"));
				dbObject.addProperty("address", rs.getString("Address"));
				dbObject.addProperty("province", rs.getString("Province"));
				dbObject.addProperty("type", rs.getString("Type"));
				dbObject.addProperty("power", rs.getString("PowerGenerated"));
				dbObject.addProperty("maintenanceDay", rs.getString("Address"));
				output=dbObject;
				
			}
			con.close();
			
		}
		catch (Exception e)
		{
			output=new JsonObject();
			output.addProperty("MESSAGE","Error while reading the power source details.");
			//output = "Error while reading the power source details.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
}
