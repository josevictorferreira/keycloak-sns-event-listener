# Keycloak SNS Event Listener

Keycloak extension that aims to publish events to a SNS(Amazon Simple Notification Service) queue and then process using Amazon SQS(Simple Queue Service).

## Installation

First, build the jar file: `./gradlew jar`

Copy the generated file `./build/libs/keycloak-sns-event-listener-1.2.17.jar` to `/opt/jboss/keycloak/standalone/deployments/`

Then add the following system variables to your keycloak service:

```
# AWS Access key id
AWS_ACCESS_KEY_ID=YOURAWSACCESSKEYID
# AWS Secret Access Key
AWS_SECRET_ACCESS_KEY=YOURAWSSECRETKEY
# AWS Region
KK_TO_SNS_REGION=aws-region
# SNS Topic Arn
KK_TO_SNS_TOPIC=YOUR_SNS_ARN_TOPIC
```

After that restart your keycloak and then enable the new event listener.

![image](https://user-images.githubusercontent.com/4625612/153782199-827d2a14-e35c-4a82-b142-5c9c1837a57a.png)
