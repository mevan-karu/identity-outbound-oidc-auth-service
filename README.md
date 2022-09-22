# identity-outbound-oidc-auth-service

## Setting up development environment.

### Prerequisite
* Install Java SE Development Kit 11
* Install Apache Maven 3.x.x(https://maven.apache.org/download.cgi#)
* Get a clone from https://github.com/wso2-incubator/identity-outbound-oidc-auth-service or download the source

### Setting up keystores and truststores.
Keystores and trust stores for development are attached with the repository source.
Since this microservice communicates with Identity Server using mTLS, certificate exchange needs to be done before the communication.
This can be done via GUI tool, keytool explorer or keytool CLI.
#### Exporting certificate from microservice keystore.
Execute following command inside the `$MICROSERVICE_HOME/resources/security` directory.
```
keytool  -export -alias localhost \
-keystore keystore.jks \
-file outbound-service.crt \
-rfc -storepass wso2carbon
```
Above command will generate a file named `outbound-service.crt`. Move that to `IS_HOME/repository/resources/security` directory.

Execute following command inside the `IS_HOME/repository/resources/security` directory to import the certifate of the microservice to the IS truststore.
```
keytool -import -alias localhost -file outbound-service.crt \
-keystore client-truststore.jks \
-storepass wso2carbon \
-noprompt
```

#### Exporting certificate from Identity Server
Execute following command inside the `IS_HOME/repository/resources/security` directory to extract the IS certificate.

```
keytool  -export -alias wso2carbon \
        -keystore wso2carbon.jks \
        -file is.crt \
        -rfc -storepass wso2carbon
```
Above command will generate a file named `is.crt`. Move that file to `$MICROSERVICE_HOME/resources/security` directory.

Execute following command inside `$MICROSERVICE_HOME/resources/security` directory.
```
keytool -import -alias wso2carbon -file wso2carbon.crt \
        -keystore client-truststore.jks \
        -storepass wso2carbon \
        -noprompt
```

### Building the project.
Both server and client can be built using the following maven command.
`mvn clean install` inside the `$PROJECT_ROOT` directory.

This will build the jars for both server and client.

### Running the microservice.
Maven build will build the microservice as a jar which can be run as a separate service.
The jar can be found in the `PROJECT_HOME/components/org.wso2.identity.outbound.oidc.auth.service/target` directory as
`org.wso2.identity.outbound.oidc.auth.service-<version>-full.jar`.

This jar needs the resources (keystores, properties files) to run. Copy the jar to the resources folder. 

Run the microservice using the following command.
```
java -Dlog4j.configurationFile=log4j2.properties -jar com.wso2.identity.asgardeo.outbound.oidc.service-<version>-full.jar
```

Once the server is running following log can be seen in the terminal.
```
TID: [1] [2022-09-21 07:58:06,124] [] : iam-cloud-outbound-oidc-service :  INFO {org.wso2.identity.outbound.oidc.auth.service.OutboundOIDCServer} - Outbound OIDC Service started successfully. Listening on port 5002
```

### Setting up Identity Server.

The microservice client jar can be found in `PROJECT_HOME/components/org.wso2.identity.outbound.oidc.auth.client/target` directory as `org.wso2.identity.outbound.oidc.auth.client-<version>.jar`

Copy that jar file to `IS_HOME/repository/components/dropins` directory.

Add following configuration to `deployment.toml` file in IS.
```
[authentication.authenticator.microservice]
name = "MicroServiceAuthenticator"
enable = true
parameters.url = "localhost:5002"
```

* Start the Identity Server and set up an Identity Provider with `Microservice-Authenticator` as the Federated Authenticator.
* Setup a Service Provider and use newly created Identity Provider in `Local & Outbound Authentication Configuration`.
* Try logging into the configured Service Provider.