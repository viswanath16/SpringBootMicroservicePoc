package com.microservice.company;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FetchEmpServiceUrl {

    private static String EMP_SER_URL;
    
    @Value("${employee-service-url:http://localhost:8091}")
	public void setGitUrl(String gitUrl) {
		setEMP_SER_URL(gitUrl);
    }
	public static String getEMP_SER_URL() {
		return EMP_SER_URL;
	}

	public static void setEMP_SER_URL(String eMP_SER_URL) {
		EMP_SER_URL = eMP_SER_URL;
	}
}