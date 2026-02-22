package com.lespinel.camel.route;

import com.lespinel.camel.dto.OrderRequest;
import com.lespinel.camel.dto.OrderResponse;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrdersRestRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Definición Endpoint POST /orders
        rest("orders")
                .description("Servicios REST para Órdenes")
                .produces("application/json").consumes("application/json")
                .post()
                    // Sección de documentación OpenAPI
                    .responseMessage()
                        .code(201)
                        .message("Orden Creada Exitosamente")
                        .responseModel(OrderResponse.class)
                    .endResponseMessage()
                    .description("Creación de Órdenes")
                    .to("direct:handleCreateOrderRequest");

        from("direct:handleCreateOrderRequest")
                .routeId("handle-create-order-request")
                .doTry()
                    .log("Processing new request ${body}")
                    .to("direct:createOrder")
                    .wireTap("direct:notifyNewOrder")
                    .log("Order creation request finished ${body}")
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
                .endDoTry()
                .doCatch(Exception.class)
                    .log(LoggingLevel.ERROR,"Error processing request ${exception}")
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
                .end();
    }
}
