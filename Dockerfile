##
## Image for ats.ps
##
FROM centos:7

## Update and package install
RUN yum -y update && yum clean all
RUN yum -y install tomcat-native bind-utils net-tools

## Set timezone
##RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

## Install Java
## Extracted to /usr/local/jvm/jdk1.8.0_201
ADD ./files/server-jre-8u201-linux-x64.tar.gz /usr/local/jvm
## ENV JAVA_HOME=/usr/local/jvm/jdk1.8.0_201
ENV JAVA_HOME=C:/dev/Android/java-1.8
ENV PATH=${PATH}:${JAVA_HOME}/bin

## Install upay.ps
ARG JAR_FILE
RUN mkdir -p /aetherit/ats/ws
RUN mkdir -p /aetherit/ats/ws/config
RUN mkdir -p /aetherit/ats/ws/logs
COPY ./config/logback-spring.xml    /aetherit/ats/ws/config/logback-spring.xml
COPY ./target/${JAR_FILE}           /aetherit/ats/ws/ws.jar

EXPOSE 8080

WORKDIR /aetherit/ws/ws

CMD java -Djava.library.path=/usr/lib/x86_64-linux-gnu -jar /aetherit/upay/ps/ps.jar
