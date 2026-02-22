package com.lespinel.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrdersCreationRouter extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:createOrder")
                .routeId("create-order")
                .log("Generating new order from #${body}")
                .bean("orderProducerService","generateOrder")
                .log("New order generated with id: ${body.id} and amount ${body.amount}");

        from("direct:notifyNewOrder")
                .routeId("kafka-order-creation-producer")
                .log("Sending new Order to Kafka")
                .marshal().json()
                .to("kafka:orders.creation");
    }

}
