FROM java:8
LABEL maintainer=wg
WORKDIR /data/java-project

EXPOSE 33333

COPY target/*.jar  /opt/

# ADD ../target/test-0.0.1-SNAPSHOT.jar  ./test-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/data/java-project/test-0.0.1-SNAPSHOT.jar"]
