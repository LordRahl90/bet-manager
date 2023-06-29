package com.betmanager.betmenager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka")
@Data
public class KafkaConfig {
    private String topic;
    private String bootstrapServers;
    private Sasl sasl;
    private Security security;
    private Key key;
    private Key value;

    @Data
    public static class Sasl {
        private String mechanism;
        public Jaas jaas;
    }

    @Data
    public static class Security {
        private String protocol;
    }

    @Data
    public static class Key {
        private String deserializer;
        private String serializer;
    }

    @Data
    public static class Jaas {
        private String config;
    }
}
