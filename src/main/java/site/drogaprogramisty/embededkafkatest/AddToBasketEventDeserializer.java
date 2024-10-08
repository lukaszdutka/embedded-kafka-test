package site.drogaprogramisty.embededkafkatest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class AddToBasketEventDeserializer implements Deserializer<AddToBasketEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AddToBasketEvent deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            return objectMapper.readValue(data, AddToBasketEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize AddToBasketEvent", e);
        }
    }
}