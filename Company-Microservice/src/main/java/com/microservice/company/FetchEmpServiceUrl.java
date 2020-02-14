package com.microservice.company;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FetchEmpServiceUrl {

    private static String EMP_URL;

    @Value("${message:default text}")
    public void setSvnUrl(String svnUrl) {
    	setEMP_URL(svnUrl);
    }

	public static String getEMP_URL() {
		return EMP_URL;
	}

	public static void setEMP_URL(String eMP_URL) {
		EMP_URL = eMP_URL;
	}
   

}