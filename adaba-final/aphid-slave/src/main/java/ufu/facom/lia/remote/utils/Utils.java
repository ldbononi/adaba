package ufu.facom.lia.remote.utils;

import ufu.facom.lia.interfaces.Location;
import ufu.facom.lia.search.interfaces.Priority;

public class Utils {
	
	public static Integer getPriority2(short location, Integer currentPriority){
		//o número 5 foi escolhido aleatoriamente apenas para dar uma conta que priorize o PV.
		if(location == Location.PV.getValue()){
			return (currentPriority + (5 - Priority.PV.getValue()));
			
		}else if(location == Location.CRITICAL_TREE.getValue()){
			return (currentPriority + (5 - Priority.CRITICAL_TREE.getValue()));
			
		}else{
			return (currentPriority + (5 - Priority.UNCERTAIN_NODE.getValue()));
		}
	}
	
	public static Integer getPriority(short location, Integer currentPriority){
		return currentPriority + (11 - location);
	}
}
