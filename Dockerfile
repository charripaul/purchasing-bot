FROM maven:3-alpine

RUN def dockerHome = tool 'myDocker'
RUN env.PATH = "${dockerHome}/bin:${env.PATH}"
       

RUN docker run hello-world

RUN curl -fsSLO https://get.docker.com/builds/Linux/x86_64/docker-17.04.0-ce.tgz \
  && tar xzvf docker-17.04.0-ce.tgz \
  && mv docker/docker /usr/local/bin \
  && rm -r docker docker-17.04.0-ce.tgz