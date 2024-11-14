package com.bookshop.oz.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

import com.bookshop.oz.model.enumeration.OrderStatus;

@Data
@NoArgsConstructor
public class Order {

    private Long orderId;

    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be a positive integer")
    private Integer customerId;

    @NotNull(message = "Location ID is required")
    private String locationId;

    @PastOrPresent(message = "Created date cannot be in the future")
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull(message = "Order status is required")
    private OrderStatus status;

    @PastOrPresent(message = "Closed date cannot be in the future")
    private LocalDateTime closedAt;

    @Positive(message = "Shop assistant ID must be a positive integer")
    private Integer shopAssistantId;
}
