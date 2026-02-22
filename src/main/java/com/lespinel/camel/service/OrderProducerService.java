package com.lespinel.camel.service;

import com.lespinel.camel.dto.OrderRequest;
import com.lespinel.camel.dto.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Clase para simular la ejecución de lógica de negocio
 * relacionada a la producción de nuevas Orders
 */
@Service
@Slf4j
public class OrderProducerService {

    public OrderResponse generateOrder(OrderRequest order){
        return OrderResponse.builder()
                .id(UUID.randomUUID().toString())
                .amount(calculateTotal(order.items()))
                .status("CREATED")
                .build();
    }

    private double calculateTotal(List<String> items){
        if(Objects.isNull(items) || items.isEmpty()) return 10d;
        return items.size() * 50;
    }

}
