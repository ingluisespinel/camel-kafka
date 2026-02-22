package com.lespinel.camel.route;

import com.lespinel.camel.dto.OrderRequest;
import com.lespinel.camel.dto.OrderResponse;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrdersConsumerRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("kafka:orders.creation")
                .routeId("kafka-orders-creation-consumer")
                .log("Consuming new message from Kafka topic ${headers[kafka.TOPIC]}")
                .unmarshal().json(OrderResponse.class)
                .log("Consumed order id ${body.id}");
    }
}
