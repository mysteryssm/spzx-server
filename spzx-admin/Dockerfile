FROM yogi_jdk17
MAINTAINER yogi
EXPOSE 1448
ADD target/spzx_admin-1.0-SNAPSHOT.jar /spzx_admin.jar

WORKDIR /
ENTRYPOINT ["java" , "-jar" , "spzx_admin.jar"]