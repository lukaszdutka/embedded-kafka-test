package site.drogaprogramisty.embededkafkatest;

public record AddToBasketEvent(String basketId, String itemId, int count) {

}
