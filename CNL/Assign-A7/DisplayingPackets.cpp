#include <iostream>
#include<fstream>
#include <iomanip>
#include<string>
using namespace std;

int main() {
	cout << "\n\n***** PACKET ANALYZER *****" << endl;
	string value, sr_no, time, source, destination, info, protocol, len;
	int count = -1, i = 0;

	int choice;
	do
	{
		ifstream file("WiresharkData.csv");
		//Reinitialize Counters
		count = -1;
		i = 0;
		cout<<"\n\nEnter which protocol packets you want to see"<<endl;
		cout<<"\n1. IP\n2. UDP\n3. TCP\n4. FTP\n0. Exit\n\nEnter Your Choice : ";
		cin>>choice;
		
		string protocolChoice; //sting to hold user packet choice
		
		switch(choice){
			case 1:
				protocolChoice="ICMPv6";
				break;
			case 2:
				protocolChoice="UDP";
				break;
			case 3:
				protocolChoice="TCP";
				break;
			case 4:
				protocolChoice="FTP";
				break;
			case 0:
				exit(1);
			default:
				protocolChoice="FTP";
				break;
		}
		
		while(file.good()) //LOOP UNTIL FILE HAS CONTENT
		{
			getline(file,sr_no,','); //GET STRING TILL ,
			getline(file,time,',');
			getline(file,source,',');
			getline(file,destination,',');
			getline(file,protocol,',');
			getline(file,len,',');
			getline(file,info,'\n');
	
			protocol = string(protocol,1,protocol.length()-2);
	
			if(protocol=="Protocol"||protocol==protocolChoice)
			{
				cout <<setw(4)<<left<<i++;
				cout <<setw(20)<<left<< string(time, 1, time.length()-2 );
				cout <<setw(40)<<left<<string(source, 1, source.length()-2 );
				cout <<setw(40)<<left<<string(destination, 1, destination.length()-2 );
				cout <<setw(20)<<left<<protocol;
				cout <<setw(18)<<left<< string(len, 1, len.length()-2 );
				cout <<string( info, 1, info.length()-2 )<<"\n";
				count++;
			}
		}
		file.close();
		
		cout<<"\n\nTotal "<<protocolChoice<<" Packet Count : "<<count;
		
	}while(choice != 0);
	return 0;
}
 
