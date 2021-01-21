FROM openjdk:8
MAINTAINER manbucy <manbucy@163.com>

ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /app
COPY target/ipp-gateway-server.jar /app

WORKDIR /app

EXPOSE 48000

ENTRYPOINT ["java","-jar","ipp-gateway-server.jar"]