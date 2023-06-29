package com.betmanager.betmenager.external.kafka;

import com.betmanager.betmenager.config.KafkaConfig;
import com.betmanager.betmenager.queues.IProducer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.UUID;


@Service
public class Producer implements IProducer {
    private KafkaProducer kafkaProducer;

    public Producer(KafkaConfig config) {
        Properties props = new Properties();
        props.put("bootstrap.servers", config.getBootstrapServers());
        props.put("sasl.mechanism", config.getSasl().getMechanism());
        props.put("security.protocol", config.getSecurity().getProtocol());
        props.put("sasl.jaas.config", config.getSasl().getJaas().getConfig());
        props.put("key.serializer", config.getKey().getSerializer());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, config.getKey().getDeserializer());
        props.put("value.serializer", config.getValue().getSerializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, config.getValue().getDeserializer());

        try {
            this.kafkaProducer = new KafkaProducer<String, String>(props);
        } catch (Exception ex) {
            // TODO: don't swallow exceptions
            ex.printStackTrace();
        }
    }

    @Override
    public void produce(String message) {
        this.kafkaProducer.send(new ProducerRecord<>("demo-topic", UUID.randomUUID().toString(), message));
        System.out.println("Producing:  " + message);
    }
}
