# jks-tester
Tool for testing JKS connections

- Copy JKS to build
- Build container
- Run container, pass variables to ENTRYPOINT

## Quickstart

`$ git pull https://github.com/kpark-tibcosoftware/jks-tester.git`  
`$ cd jks-tester`  
`$ nano Dockerfile`  

Add your JKS to the /usr/src/jks-tester directory

```Dockerfile
FROM openjdk:7

COPY src/ /usr/src/jks-tester
COPY [your-jks-file] /usr/src/jks-tester
WORKDIR /usr/src/jks-tester
RUN javac -cp .:commons-cli-1.3.1.jar Client.java
ENTRYPOINT ["java", "-cp", ".:commons-cli-1.3.1.jar", "Client"]
```

Build the image

`$ docker build -t jks-tester .`  

Run as application 

`$ docker run jks-tester -u [target-url] -k [jks-filename] -p [jks-passphrase]`

## Security Considerations
- Remove/purge volumes after testing to remove JKS from Docker environment
- Use environment variable for password instead of command line argument  
`$ JKSPASS=swordfish`  
`$ docker run jks-tester -u [target-url] -k [jks-filename] -p $JKSPASS`  

## Usage

| Short | Long | Required? | Description |
|---|---|---|---|
|-k|--keystore|yes|Name of the JKS file|
|-p|--password|yes|Passphrase for the JKS file|
|-u|--url|yes|Target URL to be tested|
|-v|--verbose|no|Enable verbose output|