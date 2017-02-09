package com.amarnath.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Employee implements Serializable {
	
	private int id;
	private String fName;
	private String lName;
	
	public Employee() {}
	
	public Employee(int id, String fName, String lName) {
		this.id = id;
		this.fName = fName;
		this.lName = lName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}
}
