package users;

import java.io.*;
import java.net.*;
import java.util.*;

public class user {
	public String username, password, name, address;
	long mobNo;
	
	public LinkedHashSet<order> userOrder = null;
	
	Scanner scan = new Scanner(System.in);
	
	public void addOrder(LinkedHashSet<order> cartLHS) {
		userOrder = cartLHS;
	}
	
	public boolean login(HashSet<user> userHS, String uName, String pWord) {
		
		if(userHS.isEmpty())
			return false;
		
		for(user u:userHS) {
			if(u.username.equals(uName)) {
				if(u.password.equals(pWord)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean register(HashSet<user> userHS) {
		System.out.print("Name	: ");
		name = readString();
		System.out.print("Mobile No. : ");
		mobNo = scan.nextLong();
		System.out.print("Address : ");
		address = readString();
		System.out.print("Username : ");
		username = scan.next();
		System.out.print("Password : ");
		password = scan.next();
		
		if(userHS.add(this)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void show(HashSet<user> userHS) {
		for(user u:userHS) {
			System.out.println(u.username + " " + u.password + " " + u.address +" " + u.mobNo + " " + " " + u.name);
		}
		
	}
	
	// To store unique values in set
	public boolean equals(Object obj){
		user or = (user)obj;
		if(or.username.equals(this.username)){
			return true;
		}
		return false;
	}
	
	// To store unique values in set
	public int hashCode() {
		return this.username.hashCode();
	}
	
	public static String readString()
	{
	    Scanner scanner = new Scanner(System.in);
	    return scanner.nextLine();
	}

	public void chat2rm()throws Exception {
		System.out.println("--------------------------");
		System.out.println("Ask Queries/Make Complaint");
		System.out.println("--------------------------");
		System.out.println("Enter 0 to stop");
		System.out.println("--------------------------");
		
		//user socket
		Socket s = new Socket("localhost",3355);
		DataInputStream dis = new DataInputStream(s.getInputStream());  
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());  
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
		
		String you = "", manager = "";
		
		while(!you.equals("0")){  
		
			System.out.print("You : ");
			you = br.readLine();
			dos.writeUTF(you);
			
			dos.flush();  
			manager=dis.readUTF();  
			
			System.out.println("Manager : " + manager);  
		}  
		  
		dos.close();  
		s.close();
		
		System.out.println("----------------------------");
		
	} 
}
