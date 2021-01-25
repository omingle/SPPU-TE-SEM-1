package users;

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
}
