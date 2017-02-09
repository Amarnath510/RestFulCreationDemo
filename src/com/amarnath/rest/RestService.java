package com.amarnath.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.amarnath.dao.EmployeeDao;
import com.amarnath.dao.EmployeeDaoImpl;
import com.amarnath.model.Employee;

@Path("/employee")
public class RestService {
	private EmployeeDao employeeDao = new EmployeeDaoImpl();
	
	// GET - All Employees.
	// Returns JSON.
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllEmployees() {
		List<Employee> employeesList = employeeDao.getAllEmployees();
		
		JSONObject json = new JSONObject();
		for (Employee emp : employeesList) {
			JSONObject content = new JSONObject();
			content.put("Id", Integer.toString(emp.getId()));
			content.put("FName", emp.getfName());
			content.put("LName", emp.getlName());
			
			json.put("Employee-" + Integer.toString(emp.getId()), content);
		}
		
		return json.toString();
	}
	
	// GET - By Employee Id.
	// This example shows how to pass parameter.
	@GET
	@Path("/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEmployeeById(@PathParam("id") String id) {
		Employee emp = employeeDao.getEmployee(Integer.parseInt(id));
		
		JSONObject json = new JSONObject();
		JSONObject content = new JSONObject();
		if (null == emp) {
			return json.toString();
		}
		
		content.put("Id", Integer.toString(emp.getId()));
		content.put("FName", emp.getfName());
		content.put("LName", emp.getlName());
		
		json.put("Employee", content);
		
		return json.toString();
	}
	
	// POST - To save a new Employee.
	// Want to pass multiple parameters.
	// Store it in a Form and pass the form here.
	@POST
	@Path("/save")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String saveEmployee(@FormParam("id") String id, 
									@FormParam("fName") String fName,
										@FormParam("lName") String lName) {
		Employee emp = new Employee(Integer.parseInt(id), fName, lName);
		boolean status = employeeDao.saveEmployee(emp);
		
		if (status) {
			return "<h1> Employee Added. </h1>";
		}
		
		return "<h1> Employee Already Present. </h1>";
	}
	
	// PUT - To update an Employee
	// To update already existing element then send the data in Form.
	@PUT
	@Path("/update")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateEmployee(@FormParam("id") String id,
									@FormParam("fName") String fName,
										@FormParam("lName") String lName) {
		Employee emp = new Employee(Integer.parseInt(id), fName, lName);
		boolean status = employeeDao.updateEmployee(emp);
		
		if (status) {
			return "<h1> Employee Updated. </h1>";
		}
		
		return "<h1> Employee NOT Present. </h1>";
	}
	
	// DELETE - To delete an Employee
	// To delete a record.
	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.TEXT_HTML)
	public String deleteEmployee(@PathParam("id") String id) {
		if ( employeeDao.deleteEmployee(Integer.parseInt(id)) ) {
			return "<h1> Employee " + id + " Deleted </h1>";
		}
		
		return "<h1> Employee Deletion Failed. </h1>";
	}
}
