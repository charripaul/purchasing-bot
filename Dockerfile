FROM maven:3-alpine


RUN docker run hello-world

COPY target/purchasing-bot-1.0.0.jar /bot.jar
CMD ["java", "-jar", "/bot.jar"]