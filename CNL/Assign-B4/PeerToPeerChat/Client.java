import java.net.*;  
import java.io.*;

class Client{  

	public static void main(String args[])throws Exception{  
	
		Socket s = new Socket("localhost", 3334);
		System.out.println("Socket Created...\n");
		
		DataInputStream din = new DataInputStream(s.getInputStream());  
		DataOutputStream dout = new DataOutputStream(s.getOutputStream());  
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
		  
		String yourMsg = "", serverMsg = "";
	
		while(!yourMsg.equalsIgnoreCase("bye")){  
		
			System.out.print("You : ");
			yourMsg = br.readLine();  
			dout.writeUTF(yourMsg);  
			dout.flush();
			
			serverMsg = din.readUTF();  
			System.out.println("Server : " + serverMsg);  
		}  
		  
		dout.close();  
		s.close();  
	}
}