package admin;

import java.io.*;
import java.net.*;
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


	public void chat2rm()throws Exception{  
		
		System.out.println("----------------------------");
		System.out.println("--- Messages from Manager --");
		System.out.println("----------------------------");
		System.out.println("Enter 0 to stop");
		System.out.println("----------------------------");
		
		
		ServerSocket ss = new ServerSocket(4040);
		
		Socket s=ss.accept();  
		
		DataInputStream dis=new DataInputStream(s.getInputStream());  
		
		DataOutputStream dos=new DataOutputStream(s.getOutputStream());  
		
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
		  
		String manager="", you="";  
		
		while(!manager.equals("0")){  
		
			manager=dis.readUTF();  
			System.out.println("Manager : " + manager);
			
			System.out.print("You : ");
			you=br.readLine();  
			dos.writeUTF(you);
			dos.flush();  
		}  
		
		dis.close();
		s.close();
		ss.close();
		
		System.out.println("----------------------------");
	}

}
