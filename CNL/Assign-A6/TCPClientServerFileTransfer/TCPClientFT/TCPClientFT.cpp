#include <iostream>
#include <sys/socket.h>
#include<arpa/inet.h>
#include<stdlib.h>
#include<string.h>
#include<fstream>
using namespace std;
#define SERVER_ADDRESS "127.0.0.1"
#define PORT 8777

int main() {
	
	struct sockaddr_in server_addr;
	int sock = socket(AF_INET, SOCK_STREAM, 0);
	
	if(sock < 0)
		cout<<"\n\nSocket Creation Failed";
	else
		cout<<"\n\nSocket Created";
		
	server_addr.sin_addr.s_addr = inet_addr(SERVER_ADDRESS);
	server_addr.sin_family = AF_INET;
	server_addr.sin_port = htons(PORT);

	int status = connect(sock, (struct sockaddr *)&server_addr, sizeof(server_addr));
	
	if(status == 0)
		cout<<"\n\nConnected to Server";
	else {
		cout<<"\n\nConnection Error";
		exit(1);
	}
	
	long long int msg_len;
	char buffer[256];
	
	int ch;
	do 
	{
		cout<<"\n\nWating for Server to Send";
		char filename[100];
		bzero((char *)filename, sizeof(filename));
		
		msg_len = recv(sock,filename,100,0);
		
		if(msg_len == -1) {
			cout<<"\n\nFilename error";
			exit(1)	;
		}
		
		cout<<"\n\nFilename : "<<filename;

		char *filebuff = new char[90000*80];

		bzero((char *)filebuff,sizeof(filebuff));
		msg_len = recv(sock, filebuff, 90000*80, 0);
		
		cout<<"\n\nFile Size : "<<msg_len;
		
		ofstream fout;
		fout.open(filename, ios::out|ios::binary);
		
		if(!fout) {
			cout<<"\n\nCannot Create File";
			exit(1);
		}
		else
		{
			fout.write(filebuff, msg_len);
			fout.close();
			cout<<"\n\nFile received";
		}
		
		cout <<"\n\nDo you want to Continue (1/0) : ";
		cin>>ch;
		
	} while(ch!=0);
	
	return 0;
}
