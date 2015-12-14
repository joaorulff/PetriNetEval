package util;

import java.util.ArrayList;

import model.Marking;



public class ArrayUtil {
	
	public static boolean contains (ArrayList<Marking> markings, Marking m){
		
		if(markings.size() == 0) return false;	
		
		for (Marking marking : markings) {
			if(marking.compare(m)){
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean equalOrGreaterThan (ArrayList<Marking> markings, Marking m){
		
		if(markings.size() == 0) return false;	
		
		for (Marking marking : markings) {
			if(m.equalOrGreater(marking)){
				return true;
			}
		}
		
		return false;
		
	}
	
	public static boolean containsForCoverability(ArrayList<Marking> coverabilityMarkings, Marking m){
		
		
		for (Marking marking : coverabilityMarkings) {
			
			if(marking.equalOrGreater(m)){
				return true;
			}
		}
		
		return false;
	}

}
