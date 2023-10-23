FROM adoptopenjdk/openjdk11:latest
MAINTAINER dariusz
COPY target/podium-together-1.0.0.jar podium-together-1.0.0.jar
ENTRYPOINT ["java","-jar","/podium-together-1.0.0.jar"]
