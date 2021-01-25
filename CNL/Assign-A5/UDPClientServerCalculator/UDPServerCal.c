#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/ioctl.h>
#include <netinet/in.h>
#include <arpa/inet.h>

struct udp_socket {
  int fd;
  struct sockaddr_in addr;
};

struct sockaddr_in create_sockaddr_in(const char *target, const int port) {
  struct sockaddr_in addr = { .sin_family = AF_INET };
  addr.sin_port = htons(port);
  addr.sin_addr.s_addr = inet_addr(target);
  return addr;
}

struct udp_socket listen_udp(const char *target, const int port) {
  struct udp_socket sock;
  sock.fd = socket(AF_INET, SOCK_DGRAM, 0);
  if (sock.fd < 0) {
    perror("sock");
    return sock;
  }

  const struct sockaddr_in addr = create_sockaddr_in(target, port);
  bind(sock.fd, (struct sockaddr *)&addr, sizeof(addr));

  static const int nonblocking = 1;
  ioctl(sock.fd, FIONBIO, &nonblocking);

  return sock;
}

int send_by_udp (const struct udp_socket *sock, char *msg, size_t length, size_t offset) {
  return sendto(sock->fd, msg, length, offset, (struct sockaddr *)&sock->addr, sizeof(sock->addr));
}

int reply_by_udp (const struct udp_socket *sock, char *msg, size_t length, size_t offset) {
  return sendto(sock->fd, msg, length, offset, (struct sockaddr *)&sock->addr, sizeof(sock->addr));
}

int recv_by_udp (const struct udp_socket *sock, char *buf, size_t length, size_t offset) {
  socklen_t addrlen = sizeof(sock->addr);
  return recvfrom(sock->fd, buf, length, offset, (struct sockaddr *)&sock->addr, &addrlen);
}

int main() {
  const struct udp_socket sock = listen_udp("127.0.0.1", 5000);
  if (sock.fd < 0) {
    perror("failed to connect. udp://127.0.0.1:5000");
    return -1;
  }

  int loop = 1;
  while (loop) {
    int offset = 0;
    int length = 0;
    char buf[4096];
    char num1[4096], num2[4096], res[4096], optr;
    int in1, in2, result;
    int opf = 0;
    while ((length = recv_by_udp(&sock, buf, 4096, offset)) > 0) {

      printf("Client's Operation: %.*s", length, buf);

      for (int i = 0, j = 0; i < length; i++) {
        if (buf[i] == '+' || buf[i] == '-' || buf[i] == '/' || buf[i] == '*') {
          optr = buf[i];
          opf = 1;
        }
        else if (opf) {
          num2[j] = buf[i];
          j++;
        }
        else {
          num1[i] = buf[i];
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

      length = sizeof(res)/sizeof(res[0]);

      send_by_udp(&sock, res, length, 0);
      offset += length;
    }
  }

  close(sock.fd);
  return 0;
}
