package admin;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import restaurants.*;

public class admin {
	String adminUsername = "admin";
	String adminPassword = "admin123";
	
	Scanner scan = new Scanner(System.in);

	public boolean adminLogin(String adUsername, String adPassword) {
		if(adUsername.equals(adminUsername)) {
			if(adPassword.equals(adminPassword)) {
				return true;
			}
		}
		return false;
	}

	//////////////////
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/sdl";
	static final String USER = "root";
	static final String PASS = "om123";
	
	public void addRest() throws Exception {
		String restName, restAddr, restCity;
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			System.out.print("Enter Restaurant Name : ");
			restName = readString();
			
			System.out.print("Enter Restaurant Address : ");
			restAddr = readString();
			
			System.out.print("Enter Restaurant City : ");
			restCity = readString();
		
			
			String sql = "Insert into restaurants(rest_name, rest_address, rest_city) values('" + restName + "', '" + restAddr + "', '" + restCity + "');";
			
			int n = st.executeUpdate(sql);
			
			if(n > 0) {
			
				System.out.println("--------------------------");
				System.out.println("Restaurant is Added...");
				System.out.println("--------------------------");
			}
		}
		catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		finally {
			st.close();
			con.close();	
		}
	}
	
	public void delRest() throws Exception {
		int restId;
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			System.out.print("Enter Restaurant ID : ");
			restId = scan.nextInt();
			System.out.println("--------------------------");
			
			String sql = "Select * from restaurants where rest_id = " + restId + ";";
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next()) {
				String sql1 = "Delete from restaurants where rest_id = " + restId + ";";
				
				int n = st.executeUpdate(sql1);
				
				if(n > 0) {
					System.out.println("Restaurant is Deleted...");
				}
			}
			else {
				System.out.println("Please... Enter the Valid Restaurant ID");
			}
			System.out.println("--------------------------");
		}
		catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		finally {
			st.close();
			con.close();	
		}
	}
	
	public void displayRest() throws Exception {
		Connection con = null;
		Statement st = null;
		
		System.out.println("----------------------------------------------------------------------------------------------------------------------");
		System.out.println("|S.N.| Rest ID |    Restaurant Name     |          Address         |    City    |   Manager Name   | Manager Mob. No.|");
		System.out.println("----------------------------------------------------------------------------------------------------------------------");
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Select restaurants.rest_id, rest_name, rest_address, rest_city, rmg_name, rmg_mob_no from restaurants left join rest_manager using(rest_id);";
			
			ResultSet rs = st.executeQuery(sql);
			int i = 1;
			
			while(rs.next()) {
				System.out.printf("|" +i + ".  | %-7d | %-22s | %-24s | %-10s | %-16s | %-15s |\n", rs.getInt("rest_id"), rs.getString("rest_name"), rs.getString("rest_address"), rs.getString("rest_city"), rs.getString("rmg_name"), rs.getString("rmg_mob_no"));
				i++;
			}
			System.out.println("----------------------------------------------------------------------------------------------------------------------");
		}
		catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		finally {
			st.close();
			con.close();	
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
