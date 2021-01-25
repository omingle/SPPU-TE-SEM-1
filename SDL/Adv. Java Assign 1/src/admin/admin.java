package admin;

import java.util.*;
import restaurants.*;

public class admin {
	String adminUsername = "admin";
	String adminPassword = "admin123";
	
	String restName;

	public boolean adminLogin(String adUsername, String adPassword) {
		if(adUsername.equals(adminUsername)) {
			if(adPassword.equals(adminPassword)) {
				return true;
			}
		}
		return false;
	}

	
	public void addRest(LinkedHashMap<String, restaurants> restLHM) {
		System.out.print("Enter Restaurant Name : ");
		restName = readString();
		restLHM.put(restName, null);
		
		System.out.println("--------------------------");
		System.out.println("Restaurant is Added...");
		System.out.println("--------------------------");
	}
	
	public void delRest(LinkedHashMap<String, restaurants> restLHM) {
		System.out.print("Enter Restaurant Name : ");
		restName = readString();
		System.out.println("--------------------------");
		
		if(restLHM.containsKey(restName)) {
			restLHM.remove(restName);
			System.out.println("Restaurant is Deleted...");
		}
		else {
			System.out.println("Please Enter Valid Restaurant Name");
		}
		System.out.println("--------------------------");
	}
	
	public void displayRest(LinkedHashMap<String, restaurants> restLHM) {
		int i = 1;
		System.out.println("--------------------------");
		System.out.println("---- Restaurants List ----");
		System.out.println("--------------------------");
		for(Map.Entry m : restLHM.entrySet()){    
		    System.out.println(i + ". " + m.getKey());
		    i++;
		}
		System.out.println("--------------------------");
	}
	
	public static String readString()
	{
	    Scanner scanner = new Scanner(System.in);
	    return scanner.nextLine();
	}

}
