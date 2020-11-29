FROM maven:3-alpine

ARG dockerHome = tool 'docker'
ENV PATH = "${dockerHome}/bin:${env.PATH}"
RUN docker run hello-world