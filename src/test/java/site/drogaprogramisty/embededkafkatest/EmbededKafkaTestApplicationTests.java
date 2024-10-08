package site.drogaprogramisty.embededkafkatest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EmbededKafkaTestApplicationTests {

    @Autowired
    private Consumer consumer;
    @Autowired
    private DifferentProducer producer;

    @Test
    void contextLoads() throws InterruptedException {
        //given
        AddToBasketEvent payload = new AddToBasketEvent("1", "5", 3);
//        String payload = "hello world!";

        //when
        producer.send("embedded-test-topic", payload);

        //then
        consumer.getLatch().await();
//        String actualPayload = consumer.getPayload();
        AddToBasketEvent actualPayload = consumer.getPayload();

//        System.out.println("expectedPayload=" + payload);
//        System.out.println("actualPayload=" + actualPayload);

        assertEquals(payload, actualPayload);
    }

}
