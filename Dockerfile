FROM openjdk:8-jdk-alpine1
EXPOSE 8080
ADD target/Demo.jar Demo.jar
ENTRYPOINT ["sh","-c","java -jar /Demo.jar"]