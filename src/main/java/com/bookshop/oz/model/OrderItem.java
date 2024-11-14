package com.bookshop.oz.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.bookshop.oz.model.enumeration.OrderStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class OrderItem {

    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Location ID is required")
    private String locationId;

    @NotNull(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be a positive integer")
    private Integer quantity;

    @NotNull(message = "Order status is required")
    private OrderStatus status;
}

