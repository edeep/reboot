package com.pradeep.reboot.service.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import com.pradeep.reboot.executer.RebootExecutor;

@Service
public class ExternalService {
	
    private static final Logger logger = LoggerFactory.getLogger(ExternalService.class);

    
	 @Autowired
	 private RestTemplate restTemplate;
	 
   
   	public Map<String,String> getDeviceDetails(String serialNo, String oui, String productClass){
   		logger.info("Inside method getDeviceDetails External Service");
   		Map<String,String> deviceDetailsMap = new HashMap<String,String>();
   		
   		// External service URL
        String url = "https://vpic.nhtsa.dot.gov/api/vehicles/getallmanufacturers?format=json";

        // Username and password
        String username = "user";
        String password = "password";

        // Encode credentials
        String encodedCredentials = Base64Utils.encodeToString((username + ":" + password).getBytes());

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedCredentials);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create HttpEntity with headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Make the request
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            // Print response
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());
            deviceDetailsMap.put("ResponseData", response.getBody());
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
   		
   		 return deviceDetailsMap ; 
   		
   	}
   	
  
   	public Map<String,String> checkCDA(String serialNo, String oui, String productClass){
   		logger.info("Inside method checkCDA");
   		Map<String,String> cdaStatusMap = new HashMap<String,String>();
   		cdaStatusMap.put("Input Serial No is", "TBD");
   		 return cdaStatusMap ; 
   		
   	}
   	

   	public Map<String,String> getGPVDetails(String serialNo, String oui, String productClass){
   		logger.info("Inside method getGPV");
   		Map<String,String> gpvDetailsMap = new HashMap<String,String>();
   		gpvDetailsMap.put("Input Serial No is", "TBD");
   		 return gpvDetailsMap ; 
   		
   	}
   	
   	
   	public Map<String,String> performRebootOne(String deviceId){
   		logger.info("Inside method rebootOne");
   		Map<String,String> rebootResultMap = new HashMap<String,String>();
   		rebootResultMap.put("Input Serial No is", "TBD");
   		 return rebootResultMap ; 
   		
   	}
   	
  
   	    public Map<String,String> performRebootAllDevices() {
   			logger.info("Inside method rebootAll");
   			Map<String,String> rebootAllResultMap = new HashMap<String,String>();
   			RebootExecutor.main(null);
   			rebootAllResultMap.put("RebootResult", "SucessOrFailure");
   	        return rebootAllResultMap ; 
   	    }
}
