package com.nupur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nupur.config.WebConfig;
import com.nupur.model.Location;
import com.nupur.model.StateData;
import com.nupur.service.StateService;

import java.util.*;

/*curl -H "Content-Type: application/json" -d '{"longitude": -77.036133,"latitude": 40.513799}' http://localhost:8080/S
tateServer/*/
@RestController
public class StateController {
	
	@Autowired
	StateService service;
	
	@RequestMapping("/")
	public String get() {
		System.out.println("In controller");
		
		return "Hello Nupur";
	}
	
	//This method is called when a HTTP POST call is made to StateServer
	//This method returns the state a given location lies in
	@PostMapping("/")
	public ResponseEntity<?> getState(@RequestBody Location loc) {
		
		service.setLoc(loc);
		String state = service.getState(loc);
		
		System.out.println( loc.toString() + " is in '"+state+"' state" );
	
		return ResponseEntity.ok().body( state );
	}
}
