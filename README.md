# RESTful Web Services Creation

## What is RESTful Web Service?
  - RESTful Web Services are basically REST Architecture based Web Services. 
  - In REST Architecture everything is a resource.

## RESTful Methods
  - GET    : Gets all the data.
  - POST   : Creates a new resource.
  - PUT    : Updates an existing resource.
  - DELETE : Deletes the resource
  - HEAD   : Returns the HTTP Header only, no Body.
  - OPTIONS: Lists out the supported operations in a web service.

  In this post we will create resources about GET, POST, PUT, DELETE.

## Project Structure
![Project Structure](https://github.com/Amarnath510/RestFulCreationDemo/blob/master/RestFulCreationDemo.png)

## Project Setup
  - Eclipse -> File -> New -> Dynamic Web Project -> Next -> Check "Generate web.xml deployment descriptor" -> Finish.
  - Open "https://jersey.java.net/download.html" and download "Jersey JAX-RS 2.0 RI bundle" or any latest available version.
  - Extract the zip and check the three directories "api", "ext", "list". Copy all the jars under it to lib folder under "Project-Root-Directory/WebContent/WEB-INF/lib".
  - We also need JSON jar for creating JSON objects. Download it from "https://mvnrepository.com/artifact/org.json/json/20140107".
  - Install Postman as Chrome Extension.
  - Install Tomcat in Eclipse.

## Update web.xml file
  - Append the following content to web.xml.

  ```xml
  <servlet>
  	<servlet-name>JAVA WS</servlet-name>
  	<servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
  	<init-param>
  		<param-name>jersey.config.server.provider.packages</param-name>
  		<param-value>com.amarnath.rest</param-value>  <!-- Package name where rest code is present -->
  	</init-param>
  	<load-on-startup>1</load-on-startup>
  </servlet>

  <servlet-mapping>
  	<servlet-name>JAVA WS</servlet-name>
  	<url-pattern>/*</url-pattern>
  </servlet-mapping>
  ```

## Annotations <br />
  Before going further we will see some important annotations.
  - @GET - Responds to GET request from Client.
  - @POST - Responds to POST request from Client.
  - @PUT - Responds to PUT request from Client.
  - @DELETE - Responds to DELETE request from Client.
  - @Path - It identifies the URI path. It can be specified on class or method.
  - @PathParam - Represents the parameter of the URI path.
  - @FormParam - Represents the parameter of the form.
  - @Produces - The @Produces annotation is used to specify the MIME media types or representations a resource can produce and send back to the client.
  - @Consumes - The @Consumes annotation is used to specify which MIME media types of representations a resource can accept, or consume, from the client.

## Returns types of RESTful resources
  - JSON - @Produces(MediaType.APPLICATION_JSON)
  - XML  - @Produces(MediaType.TEXT_XML)
  - HTML - @Produces(MediaType.TEXT_HTML)

## Implementation
  - GET
    - Below getAllEmployees will return all the Employees present in EmployeeDao.
    - Converts them to JSON object and return the JSON object as String.

    ```java
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
	```

  - POST
    - Save a new Employee record.
    - Since we are creating a new record we will use POST method.

    ```java
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
    ```

  - PUT
    - Update an existing record.
    - We will create a new record with the existing id and then update the existing record with new data.

    ```java
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
    ```

  - DELETE
    - Delete an existing record.
    - Pass id of the Employee which you want to delete.
    - Since we are passing id in URL we will extract the URL content using @PathParam annotation.

    ```java
    @DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.TEXT_HTML)
	public String deleteEmployee(@PathParam("id") String id) {
		if ( employeeDao.deleteEmployee(Integer.parseInt(id)) ) {
			return "<h1> Employee " + id + " Deleted </h1>";
		}
		
		return "<h1> Employee Deletion Failed. </h1>";
	}
    ```

## Run
  - Right click on project -> Run as -> Run on Server -> Select Tomcat.

## Testing
  - Open Postman.
  - Select "POST" from drop down.

  - Add Employee <br />
    - Give URL as "http://localhost:8081/RestFulDemo/employee/save".
    - We have to pass params to add an Employee. So click on Body. Select "x-www-form-unlencoded" and give three parameters.
  	- Make sure you give the same name as the attributes.
  		id 		1
  		fName 		Amarnath
  		lName 		Chandana
    - Finally Click on send. If the Employee is added successfully then you will see result as, `Employee Added.`.
    - If an Employee is already present with the same id then `Employee Already Present.`

  - Get All Employees <br />
    - Give URL as "http://localhost:8081/RestFulDemo/employee/all".
    - Choose GET as method type.
    - Click on send to see all the Employees present.

  - Get Employee by Id <br />
    - Give URL as "http://localhost:8081/RestFulDemo/employee/id/1". We are requesting for Employee with id as 1.
    - Choose GET as method type.
    - Click on send to see the Employee with id 1.

  - Update Employee by Id <br />
    - Give URL as "http://localhost:8081/RestFulDemo/employee/update".
    - Choose PUT as method type.
    - Click on send to update the Employee object.

  - DELETE <br />
  	- Give URL as "http://localhost:8081/RestFulDemo/employee/delete/5". We are requesting to delete Employee with id as 5.
  	- Choose DELETE as method type.
  	- After clicking send, if the call is success then the output will be `Employee 5 Deleted.` else if the id is not present then `Employee Deletion Failed.`.
	
	
## Reference
  - [RESTFul Web Services](https://www.tutorialspoint.com/restful/restful_methods.htm)
