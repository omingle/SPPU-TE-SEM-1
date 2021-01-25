#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/ioctl.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <iostream>
#include <sys/socket.h>
#include<arpa/inet.h>
#include<stdlib.h>
using namespace std;

#define PORT 8777
#define SERVER_ADDRESS "127.0.0.1"


int main() {
	struct sockaddr_in server_addr;
	
	int sock = socket(AF_INET,SOCK_STREAM,0);
	
	if(sock<0)
	    cout<<"\n\nSocket Creation Failed";
	else
	    cout<<"\n\nSocket Created Succesfully";
	
	server_addr.sin_addr.s_addr = inet_addr(SERVER_ADDRESS);
	server_addr.sin_family = AF_INET;
	server_addr.sin_port = htons(PORT);

	int status = connect(sock,(struct sockaddr *)&server_addr,sizeof(server_addr));
	
	if(status == 0)
		cout<<"\n\nConnected to the Server";
	else {
		cout<<"\n\nCannot Connect to the Server";
		exit(1);
	}

	char buffer[4096];
	
	cout<<endl;
	
	while(true)
	{
		bzero((char *)buffer,4096);
		
		cout<<"\n\nEnter Arithmetic Operation : ";
		if (fgets(buffer, 4096, stdin) == NULL) {
	      continue;
	    }
		
		send(sock,buffer,strlen(buffer),0);
		
		if(buffer[0] == '0') {
			break;
		}

		bzero(buffer,4096);
		
		recv(sock,(char*)&buffer,sizeof(buffer),0);
		cout<<"Result from server: "<<buffer;
	}
	
  return 0;
}
