import java.net.*;
import java.io.*;

public class MultiUserClient {
	
	public static void main(String args[])throws Exception{

		//client socket
		Socket s = new Socket("localhost", 3355);
		System.out.println("Socket Created...\n");
		
		DataInputStream dis = new DataInputStream(s.getInputStream());  
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());  
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  

		String you = "", server = "";

		while(!you.equalsIgnoreCase("bye")){  

			System.out.print("You : ");
			you = br.readLine();
			dos.writeUTF(you);
			
			dos.flush();  
			server = dis.readUTF();  
			
			System.out.println("Server : " + server);  
		}  
		dis.close();
		dos.close();  
		s.close();
	}
}