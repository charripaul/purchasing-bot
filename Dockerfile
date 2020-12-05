FROM openjdk:8-jre-alpine3.9
COPY target/purchasing-bot-1.0.0.jar /bot.jar
CMD ["java", "-jar", "/bot.jar"] 