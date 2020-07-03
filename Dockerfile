FROM adoptopenjdk/openjdk8:x86_64-alpine-jre8u212-b04

EXPOSE 6789

COPY build/libs/servertcp-0.1.0-SNAPSHOT.jar /app/servertcp.jar

CMD java -jar /app/servertcp.jar