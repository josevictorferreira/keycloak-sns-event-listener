# Keycloak SNS Event Listener

Keycloak extensions that aims to publish events to a SNS(Amazon Simple Notification Service) queue and then process using Amazon SQS(Simple Queue Service).

## Installation

First, build the jar file, and then copy generated file on `./build/libs/keycloak-sns-event-listener-1.2.17.jar` to `/opt/jboss/keycloak/standalone/deployments/`:

```bash
./gradlew jar
```

After that restart your keycloak and then enable the new event listener.
