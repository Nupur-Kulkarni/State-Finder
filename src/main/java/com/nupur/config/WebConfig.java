package com.nupur.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import com.nupur.model.Location;
import com.nupur.model.StateData;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.*;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.nupur")
public class WebConfig {
	
	
	/*A singleton bean of StateData is created which reads the data from JSON file and stores 
	that to HashMap statesData only once in application lifetime*/
	@Bean(initMethod = "init")
	@Scope("singleton")
	public StateData stateData() {
		return new StateData();
	}

	
}
