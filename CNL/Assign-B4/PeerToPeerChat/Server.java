import java.net.*;
import java.io.*;

class Server{
	
	public static void main(String args[])throws Exception{
		
		ServerSocket ss = new ServerSocket(3334);  
		
		System.out.println("Socket Created...");
		
		Socket s = ss.accept();  
		
		System.out.println("Connected to the Client...\n");
		
		DataInputStream din = new DataInputStream(s.getInputStream());  
		DataOutputStream dout = new DataOutputStream(s.getOutputStream());  
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
		  
		String clientMsg = "", yourMsg = "";  
		
		while(!clientMsg.equalsIgnoreCase("bye")){  
		
			clientMsg = din.readUTF();  
			System.out.println("Client : " + clientMsg);
			
			System.out.print("You : ");
			yourMsg = br.readLine();
			dout.writeUTF(yourMsg);  
			dout.flush();  
		}  
		
		din.close();  
		s.close();  
		ss.close();  
	}
}