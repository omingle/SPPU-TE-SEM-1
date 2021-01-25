package restaurants;

import java.io.*;
import java.net.*;
import java.util.*;
import restaurants.restaurants.Items;
import users.*;

public class restaurantManager extends restaurants{

	public String usernameRM;
	String passwordRM, nameRM, addressR, cityR, restNameR;
	long mobNoRM;
	
	restaurants restObj = null;
	Categories categoriesObj = new Categories(catAL);
	Items itemObj = new Items(itemsLHM);
	
	
	Scanner scan = new Scanner(System.in);

	// manager login and register
	public boolean loginRM(HashSet<restaurantManager> rmHS, String uNameRM, String pWordRM) {
		
		if(rmHS.isEmpty()) 
			return false;
			
		for(restaurantManager rm:rmHS) {
			if(rm.usernameRM.equals(uNameRM)) {
				if(rm.passwordRM.equals(pWordRM)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int registerRM(HashSet<restaurantManager> rmHS, LinkedHashMap<String, restaurants> rest) {
		System.out.print("Name	: ");
		nameRM = readString();
		System.out.print("Mobile No. : ");
		mobNoRM = scan.nextLong();
		System.out.print("Address : ");
		addressR = readString();
		System.out.print("City : ");
		cityR = readString();
		System.out.print("Restaurant Name : ");
		restNameR = readString();
		System.out.print("Username : ");
		usernameRM = scan.next();
		System.out.print("Password : ");
		passwordRM = scan.next();
		
		
		if(rest.containsKey(restNameR))
		{
			restObj = new restaurants();
			rest.put(restNameR, restObj);
		}
		else {
			return 2;
		}
			
		
		if(rmHS.add(this)) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	// manager login and register end
	

	// display manager details
	public void showRM(HashSet<restaurantManager> rmHS) {
		for(restaurantManager rm:rmHS) {
			System.out.println(rm.usernameRM + " " + rm.passwordRM + " " + rm.addressR +" " + rm.mobNoRM + " " + " " + rm.nameRM);
		}
	}
	
	public void displayRest(LinkedHashMap<String, restaurants> rest) {
		int i = 1;
		for(Map.Entry m:rest.entrySet()){  
		   System.out.println(i + ". " + m.getKey());
		   i++;
		}  
	}
	
	// adding user order
	public void setOrders(user u) {
		allOrder.add(u);
	}
	
	// displaying all orders
	public void showAllOrders() {
		
		for(int x=0;x<allOrder.size();x++) {
			user u = allOrder.get(x);
			allOrderPrio.add(u);
		}
		
		int i=1;
		if(allOrderPrio.isEmpty()) {
			System.out.println("You don't have any Order Yet");
		}
		
		while(!allOrderPrio.isEmpty()) {
			user u = allOrderPrio.poll();
			System.out.println(i + ". " + u.username + " - " + u.address + " => ");
			int totalPrice = 0;
			int j=1;
			System.out.println("--------------------------------------");
			System.out.println("SN.----Items-------------Price---Qty--");
			System.out.println("--------------------------------------");
			
			for(order io:u.userOrder){
			    System.out.printf(j + ". %-20s %5d %5d \n", io.itemName, (io.itemPrice*io.qty), io.qty);
			    totalPrice = totalPrice + (io.itemPrice*io.qty);
			    j++;
			}
			System.out.println("--------------------------------------");
			System.out.println("Total Amount :        Rs. " + totalPrice);
			System.out.println("--------------------------------------\n");
			i++;
		}

	}
	
	public user getUser(String uName) {
		for (Iterator<user> iterator = allOrder.iterator(); iterator.hasNext(); ) {
		    user u = iterator.next();
		    if(u.username.equals(uName)) {
		    	return u;
		    }
		}
		return null;
	}
	
	//approving order
	public void approveOrder(String uName) {
		if(allOrder.contains(getUser(uName))) {
		
			for(user u:allOrder) {
				if(u.username.equals(uName)) {
					System.out.println("Order Approved...");
				}
			}
		}
		else {
			System.out.println("Please Enter the Valid Username");
		}
	}

	// removing orders
	public void removeCompletedOrder(String uName) {
		try {
			int i=0;
			if(allOrder.contains(getUser(uName))) {
				if(allOrder.size()==1) {
					if(allOrder.get(0).username.equals(uName)) {
						allOrder.get(0).userOrder = null;
						allOrder.remove(0);
						System.out.println("Order Removed...");
					}
				}
				else
				{
					for (Iterator<user> iterator = allOrder.iterator(); iterator.hasNext(); ) {
					    user u = iterator.next();
					    if(u.username.equals(uName)) {
							u.userOrder = null;
							iterator.remove();
							System.out.println("Order Removed...");
						}
					}
				}
			}
			else {
				System.out.println("Please Enter the Valid Username");
			}
		}
		catch(Exception e ) {
			System.out.println("Exception : " + e.getMessage());
			System.out.println("Exception : " + e.getLocalizedMessage());
		}
	}
	

	// manager operations end
	
	// manager categories operation
	public void showCat() {
		for(int i = 0 ; i < catAL.size(); i++) {
			System.out.println(i+1 + ". " + catAL.get(i));
		}
	}
	
	public String getCat(int userCat) {
		return catAL.get(userCat-1);
	}
	
	public void addCategory(String addDelCat) {
		catAL.add(addDelCat);
		itemsLHM.put(addDelCat, new ArrayList<Items>(Arrays.asList()));
	}
	
	public void delCategory(String addDelCat) {
		if(catAL.contains(addDelCat)) {
			catAL.remove(new String(addDelCat));
			delAllItems(addDelCat);
		}
		else
			System.out.println("Category Doesn't Exist...");	
	}
	// manager categories operation end
	
	
	// manager items operation
	public void showItems(String addCat) {
		if(itemsLHM.containsKey(addCat)) {
			ArrayList<Items> it = itemsLHM.get(addCat);
			int j=1;
			for(Items i:it) {
				System.out.printf(j + ". %-20s %5s \n", i.itemName, i.itemPrice);
				j++;
			}
		}
		else
			System.out.println("Category Doesn't Exist");
	}
	
	public String getItemName(String addCat, int index) {
		ArrayList<Items> it = itemsLHM.get(addCat);
		return it.get(index).itemName;
	}
	
	public int getItemPrice(String addCat, int index) {
		ArrayList<Items> it = itemsLHM.get(addCat);
		return it.get(index).itemPrice;
	}
	
	public void addItems(String addCat, String addEditItem, int priceItem) {
		
		Items itm = new Items(addEditItem, priceItem);
		
		if(itemsLHM.containsKey(addCat)) {
			ArrayList<Items> it = itemsLHM.get(addCat);
			it.add(itm);
			itemsLHM.replace(addCat, it);
		}
		else
			System.out.println("Category Doesn't Exist");
	}

	public void delItems(String delCat, int delItem) {
		
		if(itemsLHM.containsKey(delCat)) {
			ArrayList<Items> it = itemsLHM.get(delCat);
			it.remove(it.get(delItem));
			itemsLHM.replace(delCat, it);
		}
		else
			System.out.println("Category Doesn't Exist");
	}
	
	public void delAllItems(String delCat) {
		if(itemsLHM.containsKey(delCat)) 
			itemsLHM.remove(delCat);
		else
			System.out.println("Category Doesn't Exist");
	}

	public void editItems(String addDelCat, String addEditItem,
			String newEditItem, int newEditPrice) {
		
		if(itemsLHM.containsKey(addDelCat)) {
			ArrayList<Items> it = itemsLHM.get(addDelCat);
			for(Items i:it) {
				if(i.itemName.equals(addEditItem)) {
					i.itemName = newEditItem;
					i.itemPrice = newEditPrice;
				}
			}
			
		}
		else
			System.out.println("Category Doesn't Exist");
	}
	// manager item operation end
	
	
		
	// To store unique values in set
	public boolean equals(Object obj){
		restaurantManager rm = (restaurantManager)obj;
		if(rm.usernameRM.equals(this.usernameRM)){
			return true;
		}
		return false;
	}
	
	// To store unique values in set
	public int hashCode() {
		return this.usernameRM.hashCode();
	}
	
	public static String readString()
	{
	    Scanner scanner = new Scanner(System.in);
	    return scanner.nextLine();
	}
	
	public boolean checkCity(HashSet<restaurantManager> rmHS, String city) {
		Iterator<restaurantManager> itr = rmHS.iterator();
		while(itr.hasNext()) {
			if(itr.next().cityR.equals(city))
				return true;
		}
		return false;
	}
	
	public void displayCityRest(HashSet<restaurantManager> rmHS, String city) {
		int i = 1;
		Iterator<restaurantManager> itr = rmHS.iterator();
		while(itr.hasNext()) {
			restaurantManager rm = itr.next(); 
			if(rm.cityR.equals(city)) {
				System.out.println(i + ". " + rm.restNameR);
				i++;
			}
		}
	}
	
	public restaurantManager getRest(LinkedHashMap<String, restaurants> restLHM, HashSet<restaurantManager> rmHS, String userRest) {
		if(restLHM.containsKey(userRest)) {
			for(restaurantManager rm:rmHS) {
				if(rm.restNameR.equals(userRest)) {
					return rm;
				}
			}
		}
		else
		{
			System.out.println("Please Enter Valid Restaurant Name");
			System.out.println("----------------------------------");
		}
		return null;
	}
	
	public void chat2user()throws Exception{  
		
		System.out.println("----------------------------");
		System.out.println("Queries/Complaint from Users");
		System.out.println("----------------------------");
		System.out.println("Enter 0 to stop");
		System.out.println("----------------------------");
		
		
		ServerSocket ss = new ServerSocket(3355);
		
		Socket s=ss.accept();
		
		DataInputStream dis=new DataInputStream(s.getInputStream());  
		
		DataOutputStream dos=new DataOutputStream(s.getOutputStream());  
		
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
		  
		String client="", you="";  
		
		while(!client.equals("0")){  
		
			client=dis.readUTF();  
			System.out.println("Client : " + client);
			
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

	public void chat2admin()throws Exception {
		System.out.println("--------------------------");
		System.out.println("-------- Ask Admin -------");
		System.out.println("--------------------------");
		System.out.println("Enter 0 to stop");
		System.out.println("--------------------------");
		
		//manager socket
		Socket s = new Socket("localhost",4040);
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
			
			System.out.println("Admin : " + manager);  
		}  
		  
		dos.close();  
		s.close();
		
		System.out.println("----------------------------");		
	}
}
