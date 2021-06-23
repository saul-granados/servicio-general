FROM openjdk:11-slim

RUN apt-get update && apt-get install -y --no-install-recommends libfontconfig1 && rm -rf /var/lib/apt/lists/*

ENV TZ=America/Mexico_City
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


VOLUME /tmp

ARG JAR_FILE=./target/*.jar
ADD ${JAR_FILE} app.jar

ENV JAVA_OPTS=''

ENTRYPOINT java $JAVA_OPTS -jar /app.jar