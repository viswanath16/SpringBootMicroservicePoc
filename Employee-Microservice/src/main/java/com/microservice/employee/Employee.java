package com.microservice.employee;

public class Employee {
	
	private String name;
    private String empLocation;
     
    public Employee(String name, String empLocation) {
        super();
        this.name = name;
        this.empLocation = empLocation;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getEmpLocation() {
        return empLocation;
    }
 
    public void setEmpLocation(String empLocation) {
        this.empLocation = empLocation;
    }

}
