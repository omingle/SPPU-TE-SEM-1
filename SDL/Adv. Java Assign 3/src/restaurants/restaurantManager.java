package restaurants;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

public class restaurantManager{
	
	//////////////////
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/sdl";
	static final String USER = "root";
	static final String PASS = "om123";

	Scanner scan = new Scanner(System.in);

	// manager login and register
	public boolean loginRM(String uNameRM, String pWordRM) throws Exception {
		
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Select * from rest_manager where rmg_username = '" + uNameRM + "' and rmg_password = '" + pWordRM + "';";
			
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
	
	public int registerRM(String nameRM, long mobNoRM, String addressR, int restId, String usernameRM, String passwordRM) throws Exception {
		
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql1 = "Select * from restaurants where rest_id = " + restId + ";";
			ResultSet rs1 = st.executeQuery(sql1);
				
			if(rs1.next())
			{
				String sql2 = "Select * from rest_manager where rmg_username = '" + usernameRM + "';";
				ResultSet rs2 = st.executeQuery(sql2);
								
				if(rs2.next())
				{	
					return 0;
				}
				else {
					String sql3 = "Insert into rest_manager values('" + usernameRM + "', '" + restId + "', '" + nameRM + "', '" + addressR + "', " + mobNoRM + ", '" + passwordRM + "');";
					
					int n = st.executeUpdate(sql3);
				
					if(n > 0) {
						return 1;
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		finally {
			st.close();
			con.close();	
		}
		return 2;
	}
	
	// manager login and register end
	
	public int getRestId(String rmUName) throws Exception {
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Select * from rest_manager where rmg_username = '" + rmUName + "';";
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next()) {
				return rs.getInt("rest_id");
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


	// displaying all orders
	public void showAllOrders(int restId, String deliveryStatus) throws Exception {
		Connection con1 = null;
		Statement st1 = null;
		
		Connection con2 = null;
		Statement st2 = null;
		
		try {
			Class.forName(JDBC_DRIVER);
			con1 = DriverManager.getConnection(DB_URL, USER, PASS);
			st1 = con1.createStatement();
			
			String sql1 = null;
			
			if(deliveryStatus.equals("A"))
				sql1 = "Select o.order_id, u.uname, u.uaddress, u.cart_id, o.total_price, o.payment_way, o.delivery_status from orders o inner join users u using(cart_id) where rest_id = " + restId + ";";	
			else
				sql1 = "Select o.order_id, u.uname, u.uaddress, u.cart_id, o.total_price, o.payment_way, o.delivery_status from orders o inner join users u using(cart_id) where rest_id = " + restId + " and delivery_status = '" + deliveryStatus +"';";
				
			ResultSet rs1 = st1.executeQuery(sql1);
			
			int i = 1;
			while(rs1.next()) {
				
				System.out.println(i + ". Order ID         : " + rs1.getInt("order_id"));
				System.out.println("   Customer Name    : " + rs1.getString("uname"));
				System.out.println("   Customer Address : " + rs1.getString("uaddress"));
				System.out.println("   Total Price      : " + rs1.getInt("total_price"));
				System.out.println("   Payment Way      : " + rs1.getString("payment_way"));
				System.out.println("   Delivery Status  : " + rs1.getString("delivery_status"));
				
				int cartId = rs1.getInt("cart_id");
				
				con2 = DriverManager.getConnection(DB_URL, USER, PASS);
				st2 = con2.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				String sql2 = "Select cart.item_id, items.item_name, items.item_price, cart.item_qty from items inner join cart using(item_id) where cart_id = " + cartId + ";";
				
				ResultSet rs2 = st2.executeQuery(sql2);
				
				if(rs2.next()) {
					
					rs2.previous();
					
					System.out.println("   --------------------------------------------------------------");
					System.out.println("   |S.N.| Item ID |         Item Name         |  Price  |  Qty  |");
					System.out.println("   --------------------------------------------------------------");
					
					int j = 1;
					
					while(rs2.next()) {
						System.out.printf("   | " + j + ". | %-7d | %-25s | %-7d | %-5d |\n", rs2.getInt("item_id"), rs2.getString("item_name"), rs2.getInt("item_price"), rs2.getInt("item_qty"));
						j++;
					}
					
					i++;
					System.out.println("   --------------------------------------------------------------");
					System.out.println();
				}

			}			
		}
		catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		finally {
			st1.close();
			con1.close();	
		}	
	}

	public void changeDeliveryStatus(int ordId) throws Exception {
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Update orders set delivery_status = 'Y' where order_id = " + ordId + ";";
			
			int n = st.executeUpdate(sql);
			
			if(n > 0) {
			
				System.out.println("--------------------------");
				System.out.println("Status Changed...");
				System.out.println("--------------------------");
				emptyUserCart(ordId);
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
	
	public void emptyUserCart(int ordId) throws Exception {
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql1 = "Select cart_id from orders where order_id = " + ordId + ";";
			ResultSet rs1 = st.executeQuery(sql1);
			
			if(rs1.next()) {
				String sql2 = "Delete from cart where cart_id = " + rs1.getInt("cart_id") + ";";
				int n = st.executeUpdate(sql2);
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
	// manager operations end
	
	// manager categories operation
	public void showCat(int restId) throws Exception {
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Select * from categories where rest_id = " + restId + ";";
			ResultSet rs = st.executeQuery(sql);
			
			int i = 1;
			System.out.println("----------------------------------------");
			System.out.println("|S.N.| Category ID |   Category Name   |");
			System.out.println("----------------------------------------");
			while(rs.next()) {
				System.out.printf("| " + i + ". |    %-8d | %-17s |\n", rs.getInt("cat_id"), rs.getString("cat_name"));
				i++;
			}
			System.out.println("----------------------------------------");
		}
		catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		finally {
			st.close();
			con.close();	
		}
	}

	
	public void addCategory(String addCat, int restId) throws Exception {
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Insert into categories(rest_id, cat_name) values (" + restId + ", '" + addCat + "');";
			int n = st.executeUpdate(sql);
			
			if(n > 0) {
				System.out.println("--------------------------");
				System.out.println("Category is Added...");
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
	
	public void delCategory(int delCatID, int restId) throws Exception {
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Select * from categories where cat_id = " + delCatID + ";";
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next()) {
				String sql1 = "Delete from categories where cat_id = " + delCatID + ";";
				
				int n = st.executeUpdate(sql1);
				
				if(n > 0) {
					System.out.println("Category is Deleted...");
				}
			}
			else {
				System.out.println("Please... Enter the Valid Category ID");
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
	// manager categories operation end
	
	
	// manager items operation
	public void showItems(int catId) throws Exception {
		Connection con = null;
		Statement st = null;
		
		System.out.println("-------------------------------------------------------------");
		System.out.println("|S.N.| Item ID |       Item Name        |  Item Price (Rs.) |");
		System.out.println("-------------------------------------------------------------");
		
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Select * from items where cat_id = " + catId + ";";
			
			ResultSet rs = st.executeQuery(sql);
			int i = 1;
			
			while(rs.next()) {
				System.out.printf("|" + i + ".  | %-7d | %-22s | %-17d |\n", rs.getInt("item_id"), rs.getString("item_name"), rs.getInt("item_price"));
				i++;
			}
			System.out.println("-------------------------------------------------------------");
		}
		catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		finally {
			st.close();
			con.close();	
		}	
		
	}
	
	public void addItems(int restId, int catId, String addItem, int priceItem) throws Exception {
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Insert into items(cat_id, rest_id, item_name, item_price) values(" + catId + ", " + restId + ", '" + addItem + "', " + priceItem + ");";
			
			int n = st.executeUpdate(sql);
			
			if(n > 0) {
			
				System.out.println("--------------------------");
				System.out.println("Item is Added...");
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

	public void delItems(int delItem) throws Exception {
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Select * from items where item_id = " + delItem + ";";
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next()) {
				String sql1 = "Delete from items where item_id = " + delItem + ";";
				
				int n = st.executeUpdate(sql1);
				
				if(n > 0) {
					System.out.println("Item is Deleted...");
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

	public void editItems(int editItemID, String newEditItem, int newEditPrice) throws Exception {
		Connection con = null;
		Statement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			st = con.createStatement();
			
			String sql = "Update items set item_name = '" + newEditItem + "', item_price = " + newEditPrice + " where item_id = " + editItemID + ";";
			
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

	// manager item operation end
	
		
	// To store unique values in set
	
	public static String readString()
	{
	    Scanner scanner = new Scanner(System.in);
	    return scanner.nextLine();
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
	
	
	static ExecutorService pool = Executors.newFixedThreadPool(3);
	
	public void chat2user()throws Exception{  
		
		System.out.println("----------------------------");
		System.out.println("Queries/Complaint from Users");
		System.out.println("----------------------------");
		System.out.println("Enter 0 to stop");
		System.out.println("----------------------------");
		
		ServerSocket ss = new ServerSocket(3355);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		while(true) {
			Socket s = ss.accept();
			Runnable t = new ServerClientThread(s);
			pool.execute(t);
			
		}
		
	}
}


class ServerClientThread implements Runnable {
	  Socket serverClient;

	  ServerClientThread(Socket inSocket){
		  serverClient = inSocket;
	  }
	  
	  public void run(){
		  try{
		      DataInputStream dis = new DataInputStream(serverClient.getInputStream());
		      DataOutputStream dos = new DataOutputStream(serverClient.getOutputStream());
		      BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		      String client="", you="";
		      
		      while(!client.equals("0")){  
		    	  client=dis.readUTF();  
		    	  System.out.println(client);
		    	  System.out.print("You : ");
		    	  you=br.readLine();  
		    	  dos.writeUTF(you);
		    	  dos.flush();  
		      }
	
		      dis.close();
		      dos.close();
		      serverClient.close();
		  }
		  catch(Exception ex) {
			  System.out.println(ex);
		  }
		  finally {
			  System.out.println("1 Client leave the chat...");
		  }
	  }
}