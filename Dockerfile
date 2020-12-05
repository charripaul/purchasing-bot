FROM maven:3-alpine
USER root
SHELL ["/bin/bash", "-c"]
RUN whoami