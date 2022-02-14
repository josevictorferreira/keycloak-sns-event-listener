# Keycloak SNS Event Listener

Keycloak extension that aims to publish events to a SNS(Amazon Simple Notification Service) queue and then process using Amazon SQS(Simple Queue Service).

## Installation

First, build the jar file: `./gradlew jar`

Copy the generated file `./build/libs/keycloak-sns-event-listener-1.2.17.jar` to `/opt/jboss/keycloak/standalone/deployments/`

After that, restart your keycloak and then enable the new event listener.

![image](https://user-images.githubusercontent.com/4625612/153782199-827d2a14-e35c-4a82-b142-5c9c1837a57a.png)
