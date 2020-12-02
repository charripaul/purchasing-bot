FROM maven:3-alpine
USER root
RUN whoami
RUN docker run hello-world