package com.lespinel.camel.dto;

import lombok.Builder;

@Builder
public record OrderResponse(String id, double amount, String status) {
}
