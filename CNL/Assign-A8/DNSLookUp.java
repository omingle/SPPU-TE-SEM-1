import java.net.*;
import java.util.*;

public class DNSLookUp
{
	public static void main(String[] args){
		String ip, url;
		int choice;
		Scanner scan = new Scanner(System.in);
		
		do {
			System.out.println("\n\n1. Enter IP Address\n2. Enter URL\n3. Exit");
			System.out.print("\nEnter Your Choice : ");
			choice = scan.nextInt();
			
			if(choice == 1)
			{  
				System.out.print("\nEnter IP Address : ");
				ip = scan.next();
				
				try {
					InetAddress address = InetAddress.getByName(ip);
					System.out.println("\nHost name : " + address.getHostName());   
					System.out.println("IP address : " + address.getHostAddress());
					System.out.println("Host name and IP address : " + address.toString());
				}
				catch (UnknownHostException ex) {
					System.out.println("\n\nCould not find " + ip);
				}
			}
			else if(choice == 2)
			{
				System.out.print("\nEnter URL : ");
				url = scan.next();
				
				try {
					InetAddress address = InetAddress.getByName(url);
					System.out.println("\nIP address : " + address.getHostAddress());
					System.out.println("Host name : " + address.getHostName()); 
					System.out.println("Host name and IP address : " + address.toString());
				}
				catch (UnknownHostException ex) {
					System.out.println("\n\nCould not find " + url);
				}
			}
			
		}while(choice!=3);
	}
}