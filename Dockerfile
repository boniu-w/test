FROM openjdk:11
LABEL maintainer=wg
WORKDIR /data/java-project

EXPOSE 33333

COPY target/*.jar  /opt/

ENTRYPOINT ["java","-jar","/data/java-project/test-0.0.1-SNAPSHOT.jar"]
