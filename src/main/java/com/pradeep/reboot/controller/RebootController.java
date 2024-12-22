package com.pradeep.reboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pradeep.reboot.configuration.AppConfigProperties;
import com.pradeep.reboot.executer.RebootExecutor;
import com.pradeep.reboot.model.RebootExternalServiceInput;
import com.pradeep.reboot.service.rest.DBService;
import com.pradeep.reboot.service.rest.ExternalService;

@RestController
@RequestMapping("/api")
public class RebootController {
	
    private static final Logger logger = LoggerFactory.getLogger(RebootController.class);

    @Autowired
    private ExternalService externalService;
    

    
	  private final AppConfigProperties appConfigProperties;

	    public RebootController(AppConfigProperties appConfigProperties) {
	        this.appConfigProperties = appConfigProperties;
	    }
 
    // GET request getServerDetails
	@GetMapping("/getServerDetails")
    public Map<String,String> getServerDetails() {
		logger.info("Inside method getServerDetails");
		Map<String,String> serverDetailsMap = new HashMap<String,String>();
		System.out.println("Working Directory: " + System.getProperty("user.dir"));
		serverDetailsMap.put("Custom Server ID:", appConfigProperties.getServerid());
		serverDetailsMap.put("Active Thread Count", String.valueOf(Thread.activeCount()));
        return serverDetailsMap ; 
    }

	 // GET request getServerDetails
	@PostMapping("/getDeviceDetails")
	public Map<String,String> getDeviceDetails(@RequestBody RebootExternalServiceInput input){
		logger.info("Inside method getDeviceDetails");
		Map<String,String> deviceDetailsMap = new HashMap<String,String>();
		Map<String,String> serviceResult = externalService.getDeviceDetails("serialNo", "oui", "productClass");
		String responseData = serviceResult.get("ResponseData");
		
		deviceDetailsMap.put("deviceDetails", responseData);
		 return deviceDetailsMap ; 
		
	}
	
	@PostMapping("/checkCDA ")
	public Map<String,String> checkCDA(@RequestBody RebootExternalServiceInput input){
		logger.info("Inside method checkCDA");
		Map<String,String> cdaStatusMap = new HashMap<String,String>();
		cdaStatusMap.put("Input Serial No is", input.getSerialNumber());
		 return cdaStatusMap ; 
		
	}
	
	@PostMapping("/getGPV")
	public Map<String,String> getGPVDetails(@RequestBody RebootExternalServiceInput input){
		logger.info("Inside method getGPV");
		Map<String,String> gpvDetailsMap = new HashMap<String,String>();
		gpvDetailsMap.put("Input Serial No is", input.getSerialNumber());
		 return gpvDetailsMap ; 
		
	}
	
	@PostMapping("/rebootOne")
	public Map<String,String> performRebootOne(@RequestBody RebootExternalServiceInput input){
		logger.info("Inside method rebootOne");
		Map<String,String> rebootResultMap = new HashMap<String,String>();
		rebootResultMap.put("Input Serial No is", input.getSerialNumber());
		 return rebootResultMap ; 
		
	}
	
	  // GET request getServerDetails
		@GetMapping("/rebootAll")
	    public Map<String,String> performRebootAllDevices() {
			logger.info("Inside method rebootAll");
			Map<String,String> rebootAllResultMap = new HashMap<String,String>();
			RebootExecutor.main(null);
			rebootAllResultMap.put("RebootResult", "SucessOrFailure");
	        return rebootAllResultMap ; 
	    }
   
}
