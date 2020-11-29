FROM maven:3-alpine
COPY target/purchasing-bot-1.0.0.jar /bot.jar
CMD ["java", "-jar", "/bot.jar"]