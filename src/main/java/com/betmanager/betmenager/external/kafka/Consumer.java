package com.betmanager.betmenager.external.kafka;

import com.betmanager.betmenager.config.KafkaConfig;
import com.betmanager.betmenager.domain.bet.BetService;
import com.betmanager.betmenager.dto.request.BetRequest;
import com.betmanager.betmenager.queues.IConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Logger;

@Service
public class Consumer implements IConsumer {

    private final KafkaConfig config;
    private final BetService betService;
    private final ObjectMapper objectMapper;
    private KafkaConsumer<String, String> kafkaConsumer;


    public Consumer(KafkaConfig kafkaConfig, BetService betService, ObjectMapper objectMapper) {
        this.config = kafkaConfig;
        this.betService = betService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void consume() {
        Properties props = new Properties();
        props.put("bootstrap.servers", config.getBootstrapServers());
        props.put("sasl.mechanism", config.getSasl().getMechanism());
        props.put("security.protocol", config.getSecurity().getProtocol());
        props.put("sasl.jaas.config", config.getSasl().getJaas().getConfig());
        props.put("key.serializer", config.getKey().getSerializer());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "key-consumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, config.getKey().getDeserializer());
        props.put("value.serializer", config.getValue().getSerializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, config.getValue().getDeserializer());

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singleton(config.getTopic()));
            while (true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
                for (ConsumerRecord<String, String> record : consumerRecords) {
                    Logger.getLogger(this.getClass().getName()).info("Key is: " + record.key());
                    Logger.getLogger(this.getClass().getName()).info("Value is: " + record.value());

                    BetRequest betDTO = objectMapper.readValue(record.value(), BetRequest.class);
                    System.out.println("BET-DTO: " + betDTO.toString());
                    betService.save(betDTO);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
