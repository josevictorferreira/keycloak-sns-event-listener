package com.github.josevictorferreira.keycloak.sns;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerTransaction;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

public class SNSEventListenerProvider implements EventListenerProvider {

    private SNSConfig cfg;
    private KeycloakSession session;
    private SnsClient snsClient;

    private EventListenerTransaction tx = new EventListenerTransaction(this::publishAdminEvent, this::publishEvent);

    public SNSEventListenerProvider(SNSConfig cfg, KeycloakSession session) {
        this.cfg = cfg;
        this.session = session;
        this.session.getTransactionManager().enlistAfterCompletion(tx);
        this.snsClient = SnsClient.builder()
                .region(this.cfg.getRegion())
                .build();
    }

    @Override
    public void onEvent(Event event) {
        tx.addEvent(event);
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean includeRepresentation) {
        tx.addAdminEvent(adminEvent, includeRepresentation);
    }

    @Override
    public void close() {
    }

    private void publishEvent(Event event) {
        ClientNotificationSerializer msg = ClientNotificationSerializer.create(event);
        String messageString = SNSConfig.writeAsJson(msg, true);

        this.publishNotification(messageString);
    }

    private void publishAdminEvent(AdminEvent adminEvent, boolean includeRepresentation) {
        AdminNotificationSerializer msg = AdminNotificationSerializer.create(adminEvent);
        String messageString = SNSConfig.writeAsJson(msg, true);

        this.publishNotification(messageString);
    }

    private void publishNotification(String messageString) {
        try {
            PublishRequest request = PublishRequest.builder()
                    .message(messageString)
                    .topicArn(this.cfg.getTopic())
                    .build();
            PublishResponse result = this.snsClient.publish(request);
            System.out.println(result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode());
        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            e.printStackTrace();

        } catch (Exception ex) {
            System.err.println("keycloak-to-sns ERROR sending message: " + messageString);
            ex.printStackTrace();
        }
    }
}
