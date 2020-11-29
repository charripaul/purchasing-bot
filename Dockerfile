FROM maven:3-alpine

USER root

RUN curl -fsSLO https://get.docker.com/builds/Linux/x86_64/docker-17.04.0-ce.tgz \
  && tar xzvf docker-17.04.0-ce.tgz \
  && mv docker/docker /usr/local/bin \
  && rm -r docker docker-17.04.0-ce.tgz

RUN ls
RUN docker run hello-world

COPY target/purchasing-bot-1.0.0.jar /bot.jar
CMD ["java", "-jar", "/bot.jar"]