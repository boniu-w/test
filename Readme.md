# 1. 关于docker

把Dockerfile文件与 项目的jar包 放到同一目录下, 然后执行 docker命令

1. Dockerfile

```
FROM openjdk:11
LABEL maintainer=wg

WORKDIR /opt

COPY ./test-0.0.1-SNAPSHOT.jar /opt
# COPY /usr/local/keystore/https-wg.keystore /opt

ENTRYPOINT ["nohup", "java","-jar","./test-0.0.1-SNAPSHOT.jar", "> test-001.log","2>&1 &"]
```

2. 创建镜像

   ```
   docker build -t test:0.0.1 .
   ```

   

3. 启动容器

   ```
   docker run -p 33335:33335 \
   -v /home/wg/logs/test/test-001.log:/opt/test-001.log \
   -d test:0.0.1 --name=test-001
   ```

   

4. 删除容器

   ```
   docker rm 容器id
   ```

5. 删除镜像

   ```
   docker rmi 镜像id
   ```

   

