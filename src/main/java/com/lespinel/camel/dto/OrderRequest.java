package com.lespinel.camel.dto;

import java.util.List;

public record OrderRequest(String owner, List<String> items) {}