package com.betmanager.betmenager;

import com.betmanager.betmenager.queues.IConsumer;
import com.betmanager.betmenager.queues.IProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BetManagerApplication {

    private final IProducer kafkaProducer;
    private final IConsumer kafkaConsumer;

    public BetManagerApplication(IProducer kafkaProducer, IConsumer kafkaConsumer) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaConsumer = kafkaConsumer;
    }

    public static void main(String[] args) {
        SpringApplication.run(BetManagerApplication.class, args);
    }

//    @Bean
//    CommandLineRunner sendToKafka() {
//        Runtime.getRuntime().addShutdownHook(new Thread("shutdown") {
//            @Override
//            public void run() {
//                // cleanup here!
//                System.out.println("shutting down");
//            }
//        });
//        return args -> {
//            for (int i = 0; i < 10; i++) {
//                this.kafkaProducer.produce("hello to the whole wide world");
//            }
//        };
//    }


    @Bean
    CommandLineRunner readFromKafka() {
        return args -> {
            System.out.println("Begin consume");
            kafkaConsumer.consume();
        };
    }
}
