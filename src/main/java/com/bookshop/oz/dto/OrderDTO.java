package com.bookshop.oz.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * DTO модель для /shopping_bin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderDTO {
	private Long orderId;
	private LocationPointDTO location;
	private LocalDateTime orderedAt;
	private LocalDateTime arrivedAt;
	private LocalDateTime closedAt;
	private BookProductDTOItem bookProduct;
	private Short quantity;
}
