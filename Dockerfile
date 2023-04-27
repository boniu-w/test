FROM openjdk:11
LABEL maintainer=wg

WORKDIR /opt/test

COPY ./test-0.0.1-SNAPSHOT.jar /opt
# COPY /usr/local/keystore/https-wg.keystore /opt

ENTRYPOINT ["nohup", "java","-jar","./test-0.0.1-SNAPSHOT.jar", "> test-001.log","2>&1 &"]
