package com.nupur.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nupur.model.Location;
import com.nupur.model.StateData;

@Service
public class StateService {
	
	//get singleton instance of StateData from application context
	@Autowired
	StateData d;
	
	Location loc;
	
	
	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	//this method returns the state in which input location lies in
	public String getState(Location loc) {
		
		HashMap<String, List<Location>> map = d.getStatesData();
		
		//iterate over the border points of every state
		for(String key: map.keySet()) {

			List<Location> polygon = map.get(key);
			
			boolean check = isInside(polygon, loc);
			//if input location is found inside or on the vertex or edges of state polygon
			//return state
			if(check) {
				return key;
			}
		}
		
		return "Not Found";
	}
	
	
	//this method checks if a location r is on the segment with end points p and q
	private boolean onSegment(Location p, Location q, Location r) {
		
		double px, py, rx, ry, qx, qy;
		py = p.getLatitude();
		px = p.getLongitude();
		ry = r.getLatitude();
		rx = r.getLongitude();
		qy = q.getLatitude();
		qx = q.getLongitude();
		
		//three points lie on a line if crossProduct is zero
		double crossProduct = (ry - py) * (qx - px) - (rx - px) * (qy - py);
		
		
		if( Math.abs(crossProduct) <= Math.ulp(1.0) ) {
			//now we check if point r lies between p and q
			if ( Math.min(px, qx) <= rx  && rx <= Math.max(px, qx) && Math.min(py, qy) <= ry && ry <= Math.max(py, qy) ){
				return true;
			}
		}
		
		return false;
	}
	
	//this method checks if the given point is on the vertex or edge or inside the state polygon
	private boolean isInside(List<Location> polygon, Location p) 
	{ 
		boolean inside = false;
		int numVertices = polygon.size();
		
		//if given point is on the vertex of state polygon, we return true
		if(polygon.contains(p)) {
			return true;
		}
	    
		//as the last location in polygon is always same as first location, we skip last
		//location and iterate over the rest of locations in polygon
		for(int i=0; i<numVertices-1; i++) {
			
			Location p1 = polygon.get(i);
			Location p2 = polygon.get(i+1);
			
			//check is point p is on the edge of state polygon
			if( onSegment(p1, p2, p) ) {
				return true;
			}
			
			double x,y,p1x,p1y,p2x,p2y;
			y = p.getLatitude();
			x = p.getLongitude();
			p1y = p1.getLatitude();
			p1x = p1.getLongitude();
			p2y = p2.getLatitude();
			p2x = p2.getLongitude();
			
			
			//this is based on ray casting algorithm
			//we start a ray in horizontal direction from the point to right direction and check if the ray
			//intersects with the edge
			if( y > Math.min(p1y,p2y) && y <= Math.max(p1y,p2y)) {
				//check if the ray starts before maximum x coordinate
				if( x <= Math.max(p1x,p2x)){
					//check if the ray isn't horizontal
					if(p1y != p2y) {
						//find out rays interaction with edge of the polygon
						double xIntersect = (y-p1y)*(p2x-p1x)/(p2y-p1y)+p1x;
						//ray will intersect with the edge if it starts before the intersection or if the edge is vertical
						if(p1x == p2x || x <= xIntersect) {
							//even no of intersections means point is outside and odd no of intersections means point is inside
							inside = !inside;
						}
					}
				}
			}
		}
		
		return inside;
	} 
}
