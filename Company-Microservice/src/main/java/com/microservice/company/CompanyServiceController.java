package com.microservice.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
 
@RestController
public class CompanyServiceController {
    @Autowired
    RestTemplate restTemplate;
 
    @RequestMapping(value = "/getCompanyDetails/{companyname}", method = RequestMethod.GET)
    public String getEmployee(@PathVariable String companyname) 
    {
        System.out.println("Getting Company details for " + companyname);
 
        System.out.println("End point URL  " + FetchEmpServiceUrl.getEMP_URL());
       
        String response = restTemplate.exchange("http://" + FetchEmpServiceUrl.getEMP_URL() +"/getEmployeeDetailsForCompany/{companyname}",
                                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, companyname).getBody();
 
        System.out.println("Response Received as " + response);
 
        return "Company Name -  " + companyname + " \n Company Details " + response;
    }
 
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}