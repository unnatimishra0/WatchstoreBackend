package com.unnati.dto;

import lombok.Data;

@Data
public class AddToCartRequest {
    private Long productId;
    private int quantity;  // If you want to include quantity in the request
}