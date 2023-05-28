FROM openjdk:8
LABEL maintainer=wg

ENV TZ=Asia/Shanghai

RUN mkdir -p /opt/wgproject/test
WORKDIR /opt/wgproject/test

COPY ./test-0.0.1-SNAPSHOT.jar /opt/wgproject/test

#ENTRYPOINT ["nohup", "java","-jar","./test-0.0.1-SNAPSHOT.jar", "> test-001.log","2>&1 &"]
