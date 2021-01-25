import java.net.*;
import java.util.concurrent.*;
import java.io.*;

public class MultiUserServer {
	static ExecutorService pool = Executors.newFixedThreadPool(2);
	
	public static void main(String args[])throws Exception{  
		int i = 1;
		
		ServerSocket ss = new ServerSocket(3355);
		
		System.out.println("Socket Created...");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		
		while(true) {
			Socket s = ss.accept();
			System.out.println("Connected to the Client " + i + "\n");
			
			Runnable t = new ServerClientThread(s, i);
			pool.execute(t);
			i++;
		}
		
	}
}


class ServerClientThread implements Runnable {
	  Socket serverClient;
	  int i;

	  ServerClientThread(Socket inSocket, int i){
		  serverClient = inSocket;
		  this.i = i;
	  }
	  
	  public void run(){
		  try{
		      DataInputStream dis = new DataInputStream(serverClient.getInputStream());
		      DataOutputStream dos = new DataOutputStream(serverClient.getOutputStream());
		      
		      BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		      
		      String client = "", you = "";
		      
		      while(!client.equalsIgnoreCase("bye")){  
		    	  client = dis.readUTF();  
		    	  System.out.println("Client " + i + " : " + client);
		    	  
		    	  System.out.print("You : ");
		    	  you = br.readLine();  
		    	  
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
			  System.out.println("\nClient " + i +" leave the chat...\n");
		  }
	  }
}