FROM openjdk:7

COPY src/ /usr/src/jks-tester
WORKDIR /usr/src/jks-tester
RUN javac -cp .:commons-cli-1.3.1.jar Client.java
ENTRYPOINT ["java", "-cp", ".:commons-cli-1.3.1.jar", "Client"]
