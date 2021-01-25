#include <iostream>
#include <sys/socket.h>
#include<arpa/inet.h>
#include<stdlib.h>
#include<string.h>
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

	char buffer[256];
	
	cout<<endl;
	
	while(strcmp(buffer, "bye"))
	{
		bzero((char *)buffer,256);
		string data;
		
		cout<<"\nEnter Message : ";
		getline(cin, data);
		strcpy(buffer,data.c_str());

		send(sock,buffer,strlen(buffer),0);
		
		bzero(buffer,256);
		recv(sock,(char*)&buffer,sizeof(buffer),0);
		cout<<"\nServer's Echo : "<<buffer<<endl;
	}
	
	return 0;
}



