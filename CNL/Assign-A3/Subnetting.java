import java.util.*;

public class Subnetting {
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Enter the IP Address : ");
		String ip = scan.next();
		String split_ip[] = ip.split("\\.");
		
		String split_bip[] = new String[4];
		String bin_ip = "";
		for(int i=0;i<4;i++){
			split_bip[i] = append8Zeros(Integer.toBinaryString(Integer.parseInt(split_ip[i])));
			bin_ip += split_bip[i];
		}
		
		System.out.println("\nIP Address in Binary : " + bin_ip);
		
		System.out.print("\n\nEnter the Number of Network ID bits : ");
		int netId_bits = scan.nextInt();
		
		String net_mask_bin = "";
		
		for(int i=0; i<32; i++) {
			


			if(i<netId_bits) {
				net_mask_bin = net_mask_bin + "1";
			}
			else {
				net_mask_bin = net_mask_bin + "0";
			}
		}
		
		String net_mask = binIP2decIP(net_mask_bin);
		
		
		System.out.println("\nNet Mask : " + net_mask + "  (" + net_mask_bin + ")");

		System.out.print("\n\nEnter the Number of Subnetworks to form : ");
		int noSubNet = scan.nextInt();
		
		int noSubNetBits = (int)Math.ceil(Math.log(noSubNet)/Math.log(2));
		
		String subnet_bin_ip[] = new String[noSubNet];
		String subnet_ip[] = new String[noSubNet];
		
		for(int i=0; i<noSubNet; i++) {
			String tmp_bin_ip = bin_ip;
			
			tmp_bin_ip = tmp_bin_ip.substring(0, netId_bits) + appendZeros(Integer.toBinaryString(i), noSubNetBits) + tmp_bin_ip.substring(netId_bits + noSubNetBits);
			
			subnet_bin_ip[i] = tmp_bin_ip;
		}
		
		System.out.println("\nSubnet Addresses are : ");
		
		for(int i=0; i<subnet_bin_ip.length; i++) {
			
			System.out.print(i+1 + ") ");
			
			String s = binIP2decIP(subnet_bin_ip[i]);
			System.out.print(s);
			subnet_ip[i] = s;
			System.out.println(" (" + subnet_bin_ip[i] + ")");
		}
		
		int host_per_subnet = (int)Math.pow(2, (32 - netId_bits - noSubNetBits));
		System.out.println("\n\nThere are " + host_per_subnet + " hosts per subnet");
		
		String subnet_mask_bin = "";
		
		for(int i=0; i<32; i++) {
			if(i<(netId_bits+noSubNetBits)) {
				subnet_mask_bin = subnet_mask_bin + "1";
			}
			else {
				subnet_mask_bin = subnet_mask_bin + "0";
			}
		}
		
		String subnet_mask = binIP2decIP(subnet_mask_bin);
		
		
		System.out.println("\nSubnet Mask : " + subnet_mask + "  (" + subnet_mask_bin + ")");
		
		System.out.println("\nRange of IP Addresses per subnet : ");
		
		for(int i=0; i<subnet_bin_ip.length; i++) {

			String first_host = binIP2decIP(Long.toBinaryString((Long.parseLong(subnet_bin_ip[i], 2) + 1)));
			String last_host = binIP2decIP(Long.toBinaryString((Long.parseLong(subnet_bin_ip[i], 2) + host_per_subnet - 2)));
			System.out.println(i+1 + ") " + first_host + " - " + last_host);
		}
		
		// To check if pinging is possible or not
		String cont;
		do {
			System.out.print("\n\nEnter the Source IP Address : ");
			String srcIP = scan.next();
			System.out.print("Enter the Destination IP Address : ");
			String destIP = scan.next();
			
			String split_src_ip[] = srcIP.split("\\.");
			
			String split_src_bip[] = new String[4];
			String bin_src_ip = "";
			
			for(int j=0; j<4; j++){
				split_src_bip[j] = append8Zeros(Integer.toBinaryString(Integer.parseInt(split_src_ip[j])));
				bin_src_ip += split_src_bip[j];
			}
			
			
			String split_dest_ip[] = destIP.split("\\.");
			
			String split_dest_bip[] = new String[4];
			String bin_dest_ip = "";
			
			for(int j=0; j<4; j++){
				split_dest_bip[j] = append8Zeros(Integer.toBinaryString(Integer.parseInt(split_dest_ip[j])));
				bin_dest_ip += split_dest_bip[j];
			}
			
			
			for(int i=0; i<subnet_bin_ip.length; i++) {
				
				String subnet_bin_start = subnet_bin_ip[i];
				String subnet_bin_end = Long.toBinaryString((Long.parseLong(subnet_bin_ip[i], 2) + host_per_subnet - 1)) ;	//next subnet address
				
				
				if((bin_src_ip.equals(subnet_bin_start)) || (bin_dest_ip.equals(subnet_bin_start))) {
					System.out.println("\nCannot Ping... You have selected Subnet ID");
				}
				else if((bin_src_ip.equals(subnet_bin_end)) || (bin_dest_ip.equals(subnet_bin_end))) {
					System.out.println("\nCannot Ping... You have selected Broadcast Address");
				}
				else {
					if((Long.parseLong(bin_src_ip, 2) > Long.parseLong(subnet_bin_start, 2)) && (Long.parseLong(bin_src_ip, 2) < Long.parseLong(subnet_bin_end, 2))) {
						if((Long.parseLong(bin_dest_ip, 2) > Long.parseLong(subnet_bin_start, 2)) && (Long.parseLong(bin_dest_ip, 2) < Long.parseLong(subnet_bin_end, 2))) {
							System.out.println("\nSystems can ping each other");
						}
						else {
							System.out.println("\nSystems can not ping each other");
						}
					}
				}
			}
			System.out.print("\nDo you want to ping again (Y/N) : ");
			cont = scan.next();
		}while(cont.equals("Y") || cont.contentEquals("y"));
	}
	
	static String append8Zeros(String s){
		String temp = new String("00000000");
		return temp.substring(s.length()) + s;
	}
	
	static String appendZeros(String s, int noSubNetBits){
		String temp = "";
		for(int i=0; i<noSubNetBits; i++) {
			temp =  temp + "0";
		}
		return temp.substring(s.length()) + s;
	}
	
	static String binIP2decIP(String ip) {
		String decIP = "";
		for(int j=0; j<32; j=j+8) {
			decIP = decIP + Integer.parseInt(ip.substring(j, j+8), 2);
			
			if(j<24) {
				decIP = decIP + ".";
			}
		}
		
		return decIP;
	}

}
