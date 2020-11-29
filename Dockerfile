FROM maven:3-alpine

USER root

ENV DOCKERVERSION=18.03.1-ce
RUN curl -fsSLO https://download.docker.com/linux/static/stable/x86_64/docker-${DOCKERVERSION}.tgz \
  && tar xzvf docker-${DOCKERVERSION}.tgz --strip 1 \
                 -C /usr/local/bin docker/docker \
  && rm docker-${DOCKERVERSION}.tgz

RUN service docker start
RUN docker run hello-world

COPY target/purchasing-bot-1.0.0.jar /bot.jar
CMD ["java", "-jar", "/bot.jar"]