# AsyncAPI Parser Java Wrapper ( over JavaScript Parser )

## Overview
Use this library to parse and validate AsyncAPI documents —either YAML or JSON— in your Java application. It is a Java wrapper over [JavaScript Parser](https://github.com/asyncapi/parser-js) implemented using [J2V8](https://github.com/eclipsesource/J2V8).

## Prerequisites
- Java 11
- Maven 3.x

## Installation
To install the parser, run the following commands

```
git clone https://github.com/AsyncAPITools/parser-java-wrapper
cd parser-java-wrapper
mvn clean install
```

## Usage
To use in your Java project, add following dependeny

For Maven ( pom.xml )
```
    <dependency>
      <groupId>org.asyncapi.tools</groupId>
      <artifactId>parser-java-wrapper</artifactId>
      <version>1.0.0</version>
    </dependency>
```
 
For gradle ( build.gradle )
 
```
implementation group: 'org.asyncapi.tools', name: 'parser-java-wrapper', version: '1.0.0'
```
 
## Example
 
### Example passing inline AsyncAPI
 
```
Object result = AsyncAPIParser.parse(
  "asyncapi: 2.4.0\n" +
  "info:\n" +
  "  title: Account Service\n" +
  "  version: '1.0.0'\n" +
  "  description: |\n" +
  "    Manages user accounts in the system.\n" +
  "  license:\n" +
  "    name: Apache 2.0\n" +
  "    url: https://www.apache.org/licenses/LICENSE-2.0\n" +
  "\n" +
  "servers:\n" +
  "  production:\n" +
  "    url: mqtt://test.mosquitto.org\n" +
  "    protocol: mqtt\n" +
  "    description: Test MQTT broker\n" +
  "\n" +
  "channels:\n" +
  "  user/signedup:\n" +
  "    subscribe:\n" +
  "      operationId: emitUserSignUpEvent\n" +
  "      message:\n" +
  "        $ref : '#/components/messages/UserSignedUp'\n" +
  "\n" +
  "components:\n" +
  "  messages:\n" +
  "    UserSignedUp:\n" +
  "      name: userSignedUp\n" +
  "      title: User signed up event\n" +
  "      summary: Inform about a new user registration in the system\n" +
  "      contentType: application/json\n" +
  "      payload:\n" +
  "        $ref: '#/components/schemas/userSignedUpPayload'\n" +
  "\n" +
  "  schemas:\n" +
  "    userSignedUpPayload:\n" +
  "      type: object\n" +
  "      properties:\n" +
  "        firstName:\n" +
  "          type: string\n" +
  "          description: 'foo'\n" +
  "        lastName:\n" +
  "          type: string\n" +
  "          description: 'bar'\n" +
  "        email:\n" +
  "          type: string\n" +
  "          format: email\n" +
  "          description: 'baz'\n" +
  "        createdAt:\n" +
  "          type: string\n" +
  "          format: date-time ");

System.out.println("parse result: " + result);
       
```

### Example passing a URL
 
```
Object result = AsyncAPIParser.parseFromUrl(new URL("https://raw.githubusercontent.com/asyncapi/spec/master/examples/simple.yml"));
System.out.println("stream result : " + result);
```
     
