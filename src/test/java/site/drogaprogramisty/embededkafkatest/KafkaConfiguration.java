package site.drogaprogramisty.embededkafkatest;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaZKBroker;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Bean
//    @Profile("dev")
    EmbeddedKafkaBroker broker() {
        Map<String, String> properties = new HashMap<>();
//        properties.put("listeners", "PLAINTEXT://localhost:9092,REMOTE://10.0.0.20:9093");
        properties.put("listeners", "PLAINTEXT://localhost:9092");
//        properties.put("advertised.listeners", "PLAINTEXT://localhost:9092,REMOTE://10.0.0.20:9093");
        properties.put("advertised.listeners", "PLAINTEXT://localhost:9092");
//        properties.put("listener.security.protocol.map", "PLAINTEXT:PLAINTEXT,REMOTE:PLAINTEXT");
        properties.put("listener.security.protocol.map", "PLAINTEXT:PLAINTEXT");

        return new EmbeddedKafkaZKBroker(1)
                .kafkaPorts(9092)
                .brokerProperties(properties)
                .brokerListProperty("spring.kafka.bootstrap-servers");
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "your-group-id");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AddToBasketEventDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ProducerFactory<String, AddToBasketEvent> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AddToBasketEventSerializer.class);
        // Add any additional producer configs here, e.g., retries, batch size etc.
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, AddToBasketEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
