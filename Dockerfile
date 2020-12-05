FROM maven:3-alpine
USER root

RUN export DOCKER_HOST=tcp://localhost:2375