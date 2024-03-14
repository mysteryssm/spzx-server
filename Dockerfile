FROM jdk:17
MAINTAINER yogi
ADD spzx-admin-1.0-SNAPSHOT.jar spzx_admin.jar
EXPOSE 1448
ENTRYPOINT ["java","-jar","spzx_admin.jar"]
