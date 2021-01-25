//CLIENT

#include <iostream>
#include<sys/socket.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <string.h>
#include <fstream>

using namespace std;

#define PORT 8999
#define buflen 1000;
#define SERVER_ADDRESS "127.0.0.1"

int main() {

	struct sockaddr_in server_addr;
	cout<<"\nUDP Client \n\nNote :- For receiving data, First Select Receive Option From Your End And Then Select Send Option From Other End";

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
	server_addr.sin_addr.s_addr=inet_addr(SERVER_ADDRESS);; //INADDR_ANY takes current IP Address of PC
	//htons() converts the unsigned short integer hostshort from host byte order to network byte order.

	socklen_t cli=sizeof(server_addr);
	int slen=sizeof(server_addr);
	int choice,msg_len;
	char buffer[1000];
	cout<<"\n\nEnter Message to server : ";
	cin.getline(buffer,sizeof(buffer));
	msg_len=sendto(sock,buffer,strlen(buffer),0,(struct sockaddr *)& server_addr,slen);
	do
	{
		cout<<"\n\n****** MENU ******\n1. Chat with Server\n2. Receive File\n3. Send File\n0. Exit\n\nEnter your choice : ";
		cin>>choice;
		switch(choice)
		{
		case 1:
		{
			cout<<"\n\nEnter message = 'bye' to end chat...";
			while(1)
			{
				bzero((char *)buffer,sizeof(buffer));
				cout<<"\n\nEnter message to server : ";
				cin.getline(buffer,sizeof(buffer));
				msg_len=sendto(sock,buffer,strlen(buffer),0,(struct sockaddr *)& server_addr,slen);
				bzero((char *)buffer,sizeof(buffer));

				if(msg_len<0) {
					cout<<"\n\nSending Error";
					exit(1);
				}

				msg_len=recvfrom(sock,buffer,1000,0,(struct sockaddr *) &server_addr,&cli);
				if(msg_len>0)
					cout<<"\nServer : "<<buffer;

				if(strcmp(buffer,"bye")==0)
					break;

				bzero((char *)buffer,sizeof(buffer));
			}
			break;
		}
		case 2:
		{
			char filename[100];
			bzero((char *)filename,sizeof(filename));
			msg_len=recvfrom(sock,filename,99,0,(struct sockaddr*)&server_addr,&cli);
			if(msg_len==-1){
				cout<<"\n\nFilename error";
				exit(1);
			}
			cout<<"\n\nFilename : "<<filename;

			long filesize;
			msg_len=recvfrom(sock,(void *)&filesize,sizeof(filesize),0,(struct sockaddr*)&server_addr,&cli);
			cout<<"\nFileSize : "<<filesize;
			char *filebuff=new char[filesize];


			bzero((char *)filebuff,sizeof(filebuff));
			msg_len=recvfrom(sock,filebuff,filesize,0,(struct sockaddr*)&server_addr,&cli);
			ofstream fout;
			fout.open(filename,ios::out|ios::binary);
			if(!fout) {
				cout<<"\n\nCannot Create File";
				exit(1);
			}
			else
			{
				fout.write(filebuff,filesize);
				fout.close();
				cout<<"\n\nFile received";
			}
		}
			break;

		case 3:
		{	cout<<"\n\nEnter Filename : ";
			char filename[100];
			cin>>filename;
			fstream fout1;
			fout1.open(filename,ios::in|ios::out|ios::binary);
			fout1.seekg(0,ios::end);
			int filesize=fout1.tellg(); //get file size
			char *filebuff=new char[filesize];
			fout1.seekg(0,ios::beg);
			fout1.read(filebuff,filesize); //reading file content

			msg_len=sendto(sock,filename,strlen(filename),0,(struct sockaddr *)&server_addr,slen); //send filename
						if(msg_len==-1) {
							cout<<"\n\nFilename error";
							exit(1)	;
						}

			msg_len=sendto(sock,(void *)&filesize,sizeof(filesize),0,(struct sockaddr *)&server_addr,slen); //send filesize
			if(msg_len==-1) {
				cout<<"\n\nFilesize error";
				exit(1);
			}



			msg_len=sendto(sock,filebuff,filesize,0,(struct sockaddr *)&server_addr,slen); //send file conetents
			if(msg_len==-1) {
				cout<<"\n\nFile transmission error";
				exit(1);
			}
			else
				cout<<"\n\nTransmission Successful";
			fout1.close();
		}
		break;
		}
	}while(choice!=0);

	//close(sock);
	return 0;
}



