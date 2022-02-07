package com.github.josevictorferreira.keycloak.sns;

import org.keycloak.Config.Scope;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class SNSEventListenerProviderFactory implements EventListenerProviderFactory {

    private SNSConfig cfg;
 
    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new SNSEventListenerProvider(cfg, keycloakSession);
    }   
    
    @Override
    public void init(Scope config) {
        cfg = SNSConfig.createFromScope(config);
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // nothing to do
    }

    @Override
    public void close() {
        // nothing to do
    }

    @Override
    public java.lang.String getId() {
        return "sns_event_listener";
    }
}
