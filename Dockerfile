FROM java:8

EXPOSE 33333

ADD test-0.0.1-SNAPSHOT.jar  /test-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/test-0.0.1-SNAPSHOT.jar"]
