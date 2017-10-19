# jks-tester
Tool for testing JKS connections

- ADD JKS to build
- Build container
- Run container, pass variables to ENTRYPOINT

## Quickstart

```Dockerfile
FROM openjdk:7

COPY src/ /usr/src/jks-tester
COPY [your-jks-file] /usr/src/jks-tester
WORKDIR /usr/src/jks-tester
RUN javac -cp .:commons-cli-1.3.1.jar Client.java
ENTRYPOINT ["java", "-cp", ".:commons-cli-1.3.1.jar", "Client"]
```
`$ docker build -t jks-tester .`  
`$ docker run jks-tester -u [target-url] -k [jks-filename] -p [jks-passphrase]`
