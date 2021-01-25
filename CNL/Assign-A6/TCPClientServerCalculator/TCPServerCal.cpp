#include <iostream>
#include<string.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/ioctl.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#define PORT 8777
using namespace std;

int main() {
	sockaddr_in server_addr, client_addr;

	int sock = socket(AF_INET,SOCK_STREAM,0);
	
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

	char buffer[4096];

	cout<<endl;
	
	while(true)
	{
		char num1[4096], num2[4096], res[4096], optr;
	    int in1, in2, result;
	    int opf = 0;
	    
		bzero(buffer, 4096);
		
		int n = recv(newSocket, buffer, 4096, 0);
		
		if(buffer[0] == '0') {
			break;
		}

		cout<<"\n\nClient's Operation : "<<buffer;
		
		for (int i = 0, j = 0; i < n; i++) {
	        if (buffer[i] == '+' || buffer[i] == '-' || buffer[i] == '/' || buffer[i] == '*') {
	          optr = buffer[i];
	          opf = 1;
	        }
	        else if (opf) {
	          num2[j] = buffer[i];
	          j++;
	        }
	        else {
	          num1[i] = buffer[i];
			}
      }

      in1 = atoi(num1);
      in2 = atoi(num2);

      if (optr == '+') {
        result = in1 + in2;
      }
      else if (optr == '-') {
        result = in1 - in2;
      }
      else if (optr == '*') {
        result = in1 * in2;
      }
      else if (optr == '/') {
        result = in1 / in2;
      }

	  sprintf(res, "%d", result);

      int length = sizeof(res)/sizeof(res[0]);

	  send(newSocket, (char*)&res, strlen(buffer), 0);
	  
	  cout<<"Result Returned : "<<res;
    }
	
  return 0;
}
