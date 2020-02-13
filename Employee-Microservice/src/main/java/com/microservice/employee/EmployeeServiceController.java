package com.microservice.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EmployeeServiceController {
 
	private static Map<String, List<Employee>> mapEmp = new HashMap<String, List<Employee>>();
 	static {
    	mapEmp = new HashMap<String, List<Employee>>();
 
        List<Employee> lst = new ArrayList<Employee>();
        Employee emp = new Employee("Viswa", "Edinburgh");
        lst.add(emp);
        emp = new Employee("Vijay", "Phoenix");
        lst.add(emp);
 
        mapEmp.put("Syntel", lst);
 
        lst = new ArrayList<Employee>();
        emp = new Employee("Rakesh", "Bangalore");
        lst.add(emp);
        emp = new Employee("Vishnu", "Hyderabad");
        lst.add(emp);
 
        mapEmp.put("Atos", lst);
    }
    
    
    @RequestMapping(value = "/getEmployeeDetailsForCompany/{companyname}", method = RequestMethod.GET)
    public List<Employee> getEmployee(@PathVariable String companyname) {
        System.out.println("Getting Employee details for " + companyname);
 
        List<Employee> employeeList = mapEmp.get(companyname);
        if (employeeList == null) {
        	employeeList = new ArrayList<Employee>();
            Employee emp = new Employee("Not Found", "N/A");
            employeeList.add(emp);
        }
        return employeeList;
    }

}