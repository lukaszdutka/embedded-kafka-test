package site.drogaprogramisty.embededkafkatest;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DifferentProducer {

    private final KafkaTemplate<String, AddToBasketEvent> kafkaTemplate;

    public DifferentProducer(KafkaTemplate<String, AddToBasketEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, AddToBasketEvent payload) {
        System.out.println("sending payload='" + payload + "' to topic=" + payload);
        kafkaTemplate.send(topic, payload);
    }

    public KafkaTemplate<String, AddToBasketEvent> getKafkaTemplate() {
        return kafkaTemplate;
    }
}