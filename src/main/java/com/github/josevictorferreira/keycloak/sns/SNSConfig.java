package com.github.josevictorferreira.keycloak.sns;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.Config.Scope;
import software.amazon.awssdk.regions.Region;

public class SNSConfig {

	public static final ObjectMapper snsObjectMapper = new ObjectMapper();

    Region region;
    String topic;

	public static SNSConfig createFromScope(Scope config) {
		SNSConfig cfg = new SNSConfig();
		
		cfg.region = Region.of(resolveConfigVar(config, "region", "us-east-1"));
		cfg.topic = resolveConfigVar(config, "topic", "keycloak");

		return cfg;
	}

	private static String resolveConfigVar(Scope config, String variableName, String defaultValue) {
		String value = defaultValue;
		if(config != null && config.get(variableName) != null) {
			value = config.get(variableName);
		} else {
			//try from env variables eg: KK_TO_RMQ_URL:
			String envVariableName = "KK_TO_SNS_" + variableName.toUpperCase();
			if(System.getenv(envVariableName) != null) {
				value = System.getenv(envVariableName);
			}
		}
		System.out.println("keycloak-to-sns configuration: " + variableName + "=" + value);
		return value;
	}

	public static String writeAsJson(Object object, boolean isPretty) {
		String messageAsJson = "unparsable";
		try {
			if (isPretty) {
				messageAsJson = SNSConfig.snsObjectMapper
						.writerWithDefaultPrettyPrinter().writeValueAsString(object);
			} else {
				messageAsJson = SNSConfig.snsObjectMapper.writeValueAsString(object);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return messageAsJson;
	}

	public Region getRegion() {
		return region;
	}

	public String getTopic() {
		return topic;
	}
}
