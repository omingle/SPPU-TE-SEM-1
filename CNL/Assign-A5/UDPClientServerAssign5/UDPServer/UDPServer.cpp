//SERVER

#include <iostream>
#include<sys/socket.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <string.h>
#include<unistd.h>
#include<fstream>
using namespace std;

#define PORT 8999
#define buflen 1000;

int main() {

	struct sockaddr_in server_addr,client_addr;
	cout<<"\nUDP Server \n\nNote :- For receiving data, First Select Receive Option From Your End And Then Select Send Option From Other End";
	
	//step 1: Creating Socket
	int sock;
	if((sock=socket(AF_INET,SOCK_DGRAM,0))==-1) //SOCK_DGRAM= SOCKET Type is datagram
	{											//0=PACKET TYPE USUALLY 0 or (IPPROTo_UDP)
		cout<<"\n\nSocket Create Error";
		exit(1);
	}
	//specify server address type, port as 8999 and address
	server_addr.sin_family=AF_INET;
	server_addr.sin_port=htons(PORT);
	server_addr.sin_addr.s_addr=htonl(INADDR_ANY); //INADDR_ANY takes current IP Address of PC
	//htons() converts the unsigned short integer hostshort from host byte order to network byte order.

	//BINDING socket to port
	if(bind(sock,(struct sockaddr *)&server_addr,sizeof(server_addr))==-1) {
		cout<<"\n\nBinding Error";
		exit(1);	
	}
	else
		cout<<"\n\nUDP Server Started...";

	int client_len=sizeof(client_addr);
	socklen_t cli=sizeof(client_addr);
	int choice,msg_len;
	char buffer[1000];
	bzero((char *)buffer,sizeof(buffer));
	msg_len=recvfrom(sock,buffer,1000,0,(struct sockaddr *)&client_addr,&cli);

	if(msg_len>0)
	{
		cout<<"\nClient :- "<<buffer<<endl;
		bzero((char *)buffer,sizeof(buffer));

	}
	do
	{
		cout<<"\n\n****** MENU ******\n1. Chat with Client\n2. Send File\n3. Receive File\n0. Exit\n\nEnter your choice : ";
		cin>>choice;
		switch(choice)
		{
		case 1:
			int n;
			while(1)
			{
				bzero((char *)buffer,sizeof(buffer));
				msg_len=recvfrom(sock,buffer,1000,0,(struct sockaddr *)&client_addr,&cli);

				if(msg_len>0)
				{
					cout<<"\nClient : "<<buffer<<endl;
					bzero((char *)buffer,sizeof(buffer));

				}
				cout<<"\nEnter Reply : ";
				cin.getline(buffer,sizeof(buffer));
				msg_len=sendto(sock,buffer,strlen(buffer),0,(struct sockaddr *)&client_addr,client_len);
				if(msg_len<0)
				{
					cout<<"\n\nSending error"<<endl;
				}
				if(strcmp(buffer,"bye")==0)
					break;
				bzero((char *)buffer,sizeof(buffer));
			}
			break;

		case 2:
		{
			cout<<"\n\nEnter Filename : ";
			char filename[100];
			cin>>filename;
			fstream fout;
			fout.open(filename,ios::in|ios::out|ios::binary);
			fout.seekg(0,ios::end);
			long filesize=fout.tellg(); //get file size
			char *filebuff=new char[filesize];
			fout.seekg(0,ios::beg);
			fout.read(filebuff,filesize); //reading file content

			msg_len=sendto(sock,filename,strlen(filename),0,(struct sockaddr *)&client_addr,client_len); //send filename
			
			if(msg_len==-1) {
				cout<<"\n\nFilename error";
				exit(1);
			}

			msg_len=sendto(sock,(void *)&filesize,sizeof(filesize),0,(struct sockaddr *)&client_addr,client_len); //send filesize
			if(msg_len==-1) {
				cout<<"\n\nFilesize error";
				exit(1);
			}
			
			msg_len=sendto(sock,filebuff,filesize,0,(struct sockaddr *)&client_addr,client_len); //send file conetents
			if(msg_len==-1) {
				cout<<"\n\nFile transmission error";
				exit(1);
			}
			else
				cout<<"\n\nTransmission Successful";
			fout.close();
		}
		break;
		case 3:
		{
			char filename[100];
			bzero((char *)filename,sizeof(filename));
			msg_len=recvfrom(sock,filename,99,0,(struct sockaddr*)&client_addr,&cli);
			if(msg_len==-1) {
				cout<<"\n\nFilename error";
				exit(1);
			}
			cout<<"\n\nFilename : "<<filename;

			int filesize;
			msg_len=recvfrom(sock,(void *)&filesize,sizeof(filesize),0,(struct sockaddr*)&client_addr,&cli);
			cout<<"\nFileSize : "<<filesize;
			char *filebuff=new char[filesize];


			bzero((char *)filebuff,sizeof(filebuff));
			msg_len=recvfrom(sock,filebuff,filesize,0,(struct sockaddr*)&client_addr,&cli);
			ofstream fout1;
			fout1.open(filename,ios::out|ios::binary);
			if(!fout1) {
				cout<<"\n\nCannot Create File";
				exit(1)	;
			}
			else
			{
				fout1.write(filebuff,filesize);
				fout1.close();
				cout<<"\n\nFile received";
			}
		}
		break;
		}
	}while(choice!=0);
	
	close(sock);
	return 0;
}


