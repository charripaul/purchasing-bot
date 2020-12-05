FROM maven:3-alpine
RUN whoami
RUN groupadd -g 998 root