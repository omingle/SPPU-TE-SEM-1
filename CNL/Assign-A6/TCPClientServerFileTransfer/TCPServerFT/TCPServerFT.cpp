#include <iostream>
#include <sys/socket.h>
#include<arpa/inet.h>
#include<stdlib.h>
#include<string.h>
#include<fstream>
using namespace std;

#define PORT 8777

int main() {

	sockaddr_in server_addr,client_addr;

	int sock = socket(AF_INET,SOCK_STREAM,0);
	
	if(sock<0) {
		cout<<"\n\nSocket Creation Error";
		exit(1);
	}
	else
		cout<<"\n\nSocket Created";
		
	bzero((char *)&server_addr,sizeof(server_addr));
	server_addr.sin_family = AF_INET;
	server_addr.sin_addr.s_addr = INADDR_ANY;
	server_addr.sin_port = htons(PORT);

	if(bind(sock, (struct sockaddr*)&server_addr, sizeof(server_addr)) == -1) {
		cout<<"\n\nBinding Error";
		exit(1);
	}

	if(listen(sock, 10)<0)
	{
		cout<<"\n\nListening Error";
		exit(1);
	}
	
	socklen_t socklen = sizeof(client_addr);

	int newSocket = accept(sock, (struct sockaddr*)&client_addr, &socklen);
	
	if(newSocket < 0) {
		cout<<"\n\nError While Accepting";
		exit(1);
	}
	else
		cout<<"\n\nConnection Accepted";
	
	long long int msg_len;

	int ch;
	
	while(true)
	{
		cout<<"\n\nEnter Filename : ";
		char filename[100];
		cin>>filename;
		
		fstream fout;

		msg_len = send(newSocket, filename, 100, 0); //send filename
		
		if(msg_len==-1)
		{
			cout<<"\n\nFilename Error";
			exit(1);
		}
		
		fout.open(filename, ios::in|ios::out|ios::binary);
		fout.seekg(0, ios::end);
		long long int filesize = fout.tellg(); //get file size
		char *filebuff = new char[filesize];
		fout.seekg(0, ios::beg);
		fout.read(filebuff, filesize); //reading file content

		msg_len = send(newSocket, filebuff, filesize, 0); //send file conetents
		
		if(msg_len == -1) {
			cout<<"\n\nFile Transmission Error";
			exit(1);
		}
		else
			cout<<"\n\nTransmission Successful";
		
		fout.close();
	}
	
	return 0;
}
