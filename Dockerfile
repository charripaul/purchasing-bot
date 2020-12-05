FROM maven:3-alpine
USER root

ENV DOCKER_HOST=unix:///var/run/docker.sock