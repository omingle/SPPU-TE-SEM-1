#include <iostream>
#include <sys/socket.h>
#include<arpa/inet.h>
#include<stdlib.h>
#include<string.h>

using namespace std;

#define PORT 8777

int main() {

	sockaddr_in server_addr,client_addr;

	int sock=socket(AF_INET,SOCK_STREAM,0);
	
	if(sock<0)
	{
		cout<<"\n\nSocket Creation Failed";
		exit(1);
	}
	else
		cout<<"\n\nSocket Created Successfully";
		
	bzero((char *)&server_addr,sizeof(server_addr));
	
	server_addr.sin_family = AF_INET;
	server_addr.sin_addr.s_addr = INADDR_ANY;
	server_addr.sin_port = htons(PORT);

	if(bind(sock,(struct sockaddr*)&server_addr,sizeof(server_addr))==-1) {
		cout<<"\n\nBinding Failed";
		exit(1);	
	}
	else {
		cout<<"\n\nBinded Successfully";
	}

	if(listen(sock,10)<0)
	{
		cout<<"\n\nListening Failed";
		exit(1);
	}
	else {
		cout<<"\n\nListening";
	}
	
	socklen_t socklen = sizeof(client_addr);

	int newSocket = accept(sock,(struct sockaddr*)&client_addr,&socklen);

	char buffer[256];

	cout<<endl;
	
	while(strcmp(buffer, "bye"))
	{
		bzero(buffer,256);
		recv(newSocket,buffer,255,0);
		cout<<"\nClient's Message : "<<buffer<<endl;
		
		send(newSocket,(char*)&buffer,strlen(buffer),0);
	}
	return 0;
}
