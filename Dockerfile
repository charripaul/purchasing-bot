FROM maven:3-alpine

USER root

RUN curl -fsSLO https://get.docker.com/builds/Linux/x86_64/docker-17.04.0-ce.tgz \
  && tar xzvf docker-17.04.0-ce.tgz \
  && mv docker/docker /usr/local/bin \
  && rm -r docker docker-17.04.0-ce.tgz

RUN usermod -aG docker jenkins
RUN docker run hello-world

USER jenkins

COPY target/purchasing-bot-1.0.0.jar /bot.jar
CMD ["java", "-jar", "/bot.jar"]