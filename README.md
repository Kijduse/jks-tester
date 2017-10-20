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

Save and close. Build the image

`$ docker build -t jks-tester .`  

Run as application 

`$ docker run jks-tester -u [target-url] -k [jks-filename] -p [jks-passphrase]`

Optionally you can provide the passphrase during runtime:

`$ docker run -it jks-tester -u [target-url] -k [jk-filename]`

## Security Considerations
- Remove/purge volumes after testing to remove JKS from Docker environment
- Use environment variable for password instead of command line argument  
`$ JKSPASS=swordfish`  
`$ docker run jks-tester -u [target-url] -k [jks-filename] -p $JKSPASS`  
- Provide password manually to jks-tester by not using the -p argument

## Usage

| Short | Long | Required? | Description |
|---|---|---|---|
|-k|--keystore|yes|Name of the JKS file|
|-p|--password|no|Passphrase for the JKS file|
|-u|--url|yes|Target URL to be tested|
|-v|--verbose|no|Enable verbose output|

If password is not provided as an argument, you will be prompted by jks-tester at runtime.