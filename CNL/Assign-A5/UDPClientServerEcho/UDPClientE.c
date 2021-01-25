#include<sys/types.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<string.h>
#include<arpa/inet.h>
#include<string.h>
#include<arpa/inet.h>
#include<stdio.h>

#define MAXLINE 1024

int main()
{
	int sockfd;
	int n;
	
	socklen_t len;
	
	char sendline[1024],recvline[1024];
	struct sockaddr_in servaddr;
	
	strcpy(sendline,"");
	
	int flag;
		
	sockfd = socket(AF_INET,SOCK_DGRAM,0);
	
	bzero(&servaddr,sizeof(servaddr));
	
	servaddr.sin_family=AF_INET;
	servaddr.sin_addr.s_addr=inet_addr("127.0.0.1");
	servaddr.sin_port=htons(5035);
	
	connect(sockfd, (struct sockaddr*)&servaddr, sizeof(servaddr));
	
	len = sizeof(servaddr);
		
	do {
		printf("\nEnter the message : ");
		scanf("%s",sendline);

		sendto(sockfd, sendline, MAXLINE, 0, (struct sockaddr*)&servaddr, len);
		
		n = recvfrom(sockfd, recvline, MAXLINE, 0, NULL, NULL);
		
		recvline[n] = 0;
		
		printf("\nServer's Echo : %s\n\n",recvline);
		
		printf("\nDo you want to continue [1/0] : ");
		scanf("%d", &flag);
		
	} while(flag == 1);
	
	return 0;
}
