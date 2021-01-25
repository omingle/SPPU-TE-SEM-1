package users;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

public class user {
	Scanner scan = new Scanner(System.in);
	
		
	//////////////////
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/sdl";
	static final String USER = "root";
	static final String PASS = "om123";

	public boolean login(String uName, String pWord) throws Exception {
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Select * from users where username = '" + uName + "' and upassword = '" + pWord + "';";
			
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next()) {
				return true;
			}
		}
		catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		finally {
			st.close();
			con.close();	
		}
		return false;
	}
	
	public boolean register(String name, long mobNo, String address, String city, String username, String password) throws Exception {
		
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql1 = "Select * from users where username = '" + username + "';";
			ResultSet rs1 = st.executeQuery(sql1);
			
			if(rs1.next()) {
				return false;
			}
			
			String sql2 = "Select count(*) from users;";
			ResultSet rs2 = st.executeQuery(sql2);
			rs2.next();
			
			String sql3 = "Insert into users values('" + username + "', '" + name + "', " + mobNo + ", '" + address + "', '" + city + "', '" + password + "', " + (rs2.getInt(1)+1) + ");";
			
			int n = st.executeUpdate(sql3);
			
			if(n > 0) {
					return true;
			}
		}
		catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		finally {
			st.close();
			con.close();	
		}
		return false;
	}
	
	public void displayCityRest(String username) throws Exception {
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql1 = "Select ucity from users where username = '" + username + "';";
			ResultSet rs1 = st.executeQuery(sql1);
			
			if(rs1.next()) {
				System.out.println("--------------------------");
				System.out.println("--- Select Restaurant ----");
				
				
				String sql2 = "Select * from restaurants where rest_city = '" + rs1.getString("ucity") + "';";
				
				ResultSet rs2 = st.executeQuery(sql2);
				
				int i = 1;
				System.out.println("--------------------------------------------------------------------");
				System.out.println("|S.N.| Rest ID |    Restaurant Name     |          Address         |");
				System.out.println("--------------------------------------------------------------------");
				
				while(rs2.next()) {
					System.out.printf("|" + i + ".  | %-7d | %-22s | %-24s |\n", rs2.getInt("rest_id"), rs2.getString("rest_name"), rs2.getString("rest_address"));
					i++;
				}
				System.out.println("--------------------------------------------------------------------");
			}
			else {
				System.out.println("------------------------------------------------------");
				System.out.println("Sorry.... Our Service is not available in your city...");
				System.out.println("------------------------------------------------------");
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

	public static String readString()
	{
	    Scanner scanner = new Scanner(System.in);
	    return scanner.nextLine();
	}

	public void chat2rm(String username)throws Exception {
		String uname = null;
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Select uname from users where username = '" + username + "';";
			ResultSet rs = st.executeQuery(sql);
			
			rs.next();
			uname = rs.getString("uname");
		}
		catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		finally {
			st.close();
			con.close();	
		}
		
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
			dos.writeUTF(uname + " : " + you);
			
			dos.flush();  
			manager = dis.readUTF();  
			
			System.out.println("Manager : " + manager);  
		}  
		dis.close();
		dos.close();  
		s.close();
		
		System.out.println("----------------------------");
		
	}

	public int getUserRest() throws Exception {
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			System.out.print("Enter Restaurant ID : ");
			int userRestID = scan.nextInt();
			
			String sql = "Select * from restaurants where rest_id = " + userRestID + ";";
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next()) {
				return userRestID;
			}
		}
		catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		finally {
			st.close();
			con.close();	
		}
		
		return 0;
	}

	public void addToCart(int cartId, int userItemID, int qty) throws Exception {
		
		Connection con = null;
		Statement st = null;

		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Insert into cart values (" + cartId + ", " + userItemID + ", " + qty + ");";
			
			int n = st.executeUpdate(sql);
			
			if(n > 0) {
			
				System.out.println("--------------------------");
				System.out.println("Item is Added to Cart...");
				System.out.println("--------------------------");
			}
		}
		catch(java.sql.SQLIntegrityConstraintViolationException e) {
			System.out.println("--------------------------");
			System.out.println("Item is Already Added to Cart...");
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

	public int getCartId(String username) throws Exception {
		
		Connection con = null;
		Statement st = null;

		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Select cart_id from users where username = '" + username + "';";
			
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			
			return(rs.getInt(1));
		}
		catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		finally {
			st.close();
			con.close();	
		}
		return 0;
	}

	public void showCart(int cartID) throws Exception {
		
		Connection con = null;
		Statement st = null;
		int totalAmount= 0;
		
		System.out.println("--------------------------------------------------------------");
		System.out.println("|S.N.| Item ID |         Item Name         |  Price  |  Qty  |");
		System.out.println("--------------------------------------------------------------");
		
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Select cart.item_id, items.item_name, items.item_price, cart.item_qty from items inner join cart using(item_id) where cart_id = " + cartID + ";";
			
			ResultSet rs = st.executeQuery(sql);
			int i = 1;
			
			while(rs.next()) {
				System.out.printf("| " + i + ". | %-7d | %-25s | %-7d | %-5d |\n", rs.getInt("item_id"), rs.getString("item_name"), rs.getInt("item_price"), rs.getInt("item_qty"));
				totalAmount = totalAmount + (rs.getInt("item_price") * rs.getInt("item_qty"));
				i++;
			}
		}
		catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		finally {
			st.close();
			con.close();	
		}	
		
		System.out.println("--------------------------------------------------------------");
		System.out.println("\t\t Total Amount :            Rs. " + totalAmount);
	}

	public void removeItem(int cartID, int rcItem) throws Exception {
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Select * from cart where cart_id = " + cartID + " and item_id = " + rcItem + ";";
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next()) {
				String sql1 = "Delete from cart where cart_id = " + cartID + " and item_id = " + rcItem + ";";
				
				int n = st.executeUpdate(sql1);
				
				if(n > 0) {
					System.out.println("Item is Removed...");
				}
			}
			else {
				System.out.println("Please... Enter the Valid Item ID");
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

	public void removeAllItems(int cartID) throws Exception {
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Delete from cart where cart_id = " + cartID + ";";
			
			int n = st.executeUpdate(sql);
			
			System.out.println("All items are removed...");
			
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

	public void changeItem(int cartID, int rcItem, int nQty) throws Exception {
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Update cart set item_qty = " + nQty + " where cart_id = " + cartID + " and item_id = " + rcItem + ";";
			
			int n = st.executeUpdate(sql);
			
			if(n > 0) {
			
				System.out.println("--------------------------");
				System.out.println("Item Updated...");
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

	public void setOrders(String username, int cartID, int userRestID, int payWay) throws Exception {
		Connection con = null;
		Statement st = null;
		
		int totalAmount = 0;
		
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql1 = "Select items.item_price, cart.item_qty from items inner join cart using(item_id) where cart_id = " + cartID + ";";
			
			ResultSet rs = st.executeQuery(sql1);
			
			while(rs.next()) {
				totalAmount = totalAmount + (rs.getInt("item_price") * rs.getInt("item_qty"));
			}
			
			String pWay = null;
			
			if(payWay == 1)
				pWay = "Credit Card";
			else if(payWay == 2)
				pWay = "Debit Card";
			else if(payWay == 3)
				pWay = "Cash";
			
			String sql2 = "Insert into orders(username, cart_id, rest_id, total_price, payment_way, delivery_status) values('" + username + "', " + cartID + ", " + userRestID + ", " + totalAmount + ", '" + pWay + "', 'N');";
			
			int n = st.executeUpdate(sql2);
			
			if(n > 0) {
			
				System.out.println("--------------------------");
				System.out.println("Ordered Successfully...");
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
}
