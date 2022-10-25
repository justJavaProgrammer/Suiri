# Docker file to local development

FROM maven:3.8.5-jdk-11-slim

WORKDIR suiribot

COPY . .

ENTRYPOINT mvn spring-boot:run
