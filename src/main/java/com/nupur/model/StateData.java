package com.nupur.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

public class StateData {
	
	public static HashMap<String, List<Location>> statesData; 
	
	public StateData() {
		statesData = new HashMap<>();
	}
	
	public void init() {
		System.out.println("Initializing states Data");
		
		//read states.json file from src/main/resources and parse it using JSONParser
		//store parsed data to statesData hashmap
		Resource resource = new ClassPathResource("states.json");
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader(resource.getFile())){
			Object obj = jsonParser.parse(reader);
			JSONArray state = (JSONArray) obj;
			
			
			state.forEach( s -> parseStateObject( (JSONObject) s ) );
			
//			for(String key: statesData.keySet()) {
//				List<Location> l = statesData.get(key);
//				System.out.println(key+": "+ l.get(0).toString());
//			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void parseStateObject(JSONObject s) {
		
		List<Location> borderPoints = new ArrayList<>();
		JSONArray border = (JSONArray) s.get("border");
		
		border.forEach( b -> addToList( (JSONArray) b, borderPoints ) );
		String name = (String) s.get("state");
		
		statesData.put(name, borderPoints);
		
	}

	private static void addToList(JSONArray b, List<Location> borderPoints) {
		
		Location loc = new Location( (double) b.get(0), (double) b.get(1));
		borderPoints.add(loc);
	}

	public static HashMap<String, List<Location>> getStatesData() {
		return statesData;
	}
	
	
}
