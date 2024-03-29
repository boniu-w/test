1. Dockerfile

```dockerfile
FROM openjdk:11
LABEL maintainer=wg

WORKDIR /opt

COPY ./test-0.0.1-SNAPSHOT.jar /opt
# COPY /usr/local/keystore/https-wg.keystore /opt

ENTRYPOINT ["nohup", "java","-jar","./test-0.0.1-SNAPSHOT.jar", "> test-001.log","2>&1 &"]
```

2. 创建镜像, 把Dockerfile 和 jar包 放到同一个目录下

   ```shell
   docker build -t test:0.0.1 .
   ```



3. 启动容器

   ```shell
   docker run -p 33335:33335 \
   -d test:0.0.1 --name=test-001
   ```



4. 删除容器

   ```shell
   docker rm 容器id
   ```

5. 删除镜像

   ```shell
   docker rmi 镜像id
   ```



6. 进入一个运行中的容器

   ```sh
   docker exec -it 030157fb3849 /bin/bash
   ```

7. 退出容器

   ```shell
   exit
   ```






# 依赖



 

| groupid                    | artifactid           | version     |
| -------------------------- | -------------------- | ----------- |
| javax.validation           | validation-api       | 2.0.1.Final |
| junit                      | junit                | 4.13.2      |
| com.fasterxml.jackson.core | jackson-annotations  | 2.14.2      |
| com.fasterxml.jackson.core | jackson-databind     | 2.14.2      |
| org.springframework        | spring-context       | 6.0.9       |
| org.apache.commons         | commons-lang3        | 3.12.0      |
| org.apache.commons         | commons-collections4 | 4.4         |
|                            |                      |             |
|                            |                      |             |
|                            |                      |             |
|                            |                      |             |
|                            |                      |             |
|                            |                      |             |

