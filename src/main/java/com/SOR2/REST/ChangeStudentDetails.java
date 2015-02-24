package com.SOR2.REST;

// Interface is optioneel voor RESTful services
public interface ChangeStudentDetails {
	Student changeName(Student student);
	Student getName();
}
