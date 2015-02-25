package com.SOR2.REST;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
//Routing /services/rest/Student
@Path("/Student")
public class ChangeStudentDetailsImpl implements ChangeStudentDetails{
	
	@POST
	// Routing /services/rest/Student/changeName
	@Path("/changeName")
	@Produces(MediaType.APPLICATION_XML)
	public Student changeName(Student student) {
		student.setName("HELLO " + student.getName());
		 return student;
	}
	
	@GET
	// Routing /services/rest/Student/getName
	@Path("/getName")
	@Produces(MediaType.APPLICATION_XML)
	public Student getName() {
	    Student student = new Student();
	    student.setName("Rockey"); 
	    return student /*type(MediaType.APPLICATION_JSON)*/;
	}
	
	@GET
	// Routing /services/rest/Student/getStudent
	@Path("/getStudent")
	@Produces(MediaType.APPLICATION_XML)
	public Student getStudent() {
	    Student student = new Student();
	    student.setName("Rockey"); 
	    student.setLastName("Williams"); 
	    student.setAge(32); 
	    return student;
	}

}