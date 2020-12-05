FROM maven:3-alpine
USER root

RUN export DOCKER_HOST=unix:///var/run/docker.sock