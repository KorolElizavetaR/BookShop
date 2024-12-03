package com.bookshop.oz.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import com.bookshop.oz.model.enumeration.OrderStatus;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id", nullable = false, updatable = false)
	private Long orderId;

	@NotNull(message = "Customer is required")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Person customer;

	@NotNull(message = "Location ID is required")
	@ManyToOne(fetch = FetchType.LAZY) // Establish a ManyToOne relationship
	@JoinColumn(name = "location_id", nullable = false)
	private LocationPoint location;

	@PastOrPresent(message = "Created date cannot be in the future")
	@Column(name = "ordered_at", nullable = false)
	@CreationTimestamp
	private LocalDateTime orderedAt;
	
	@PastOrPresent(message = "Created date cannot be in the future")
	@Column(name = "arrived_at", nullable = false)
	@CreationTimestamp
	private LocalDateTime arrivedAt;

	@JdbcType(PostgreSQLEnumJdbcType.class)
	@NotNull(message = "Order status is required")
	private OrderStatus status;

	@Column(name = "closed_at")
	@PastOrPresent(message = "Closed date cannot be in the future")
	private LocalDateTime closedAt;

	/**
	 * Назначается в момент закрытия заказа
	 */
	@Column(name = "shop_assistant_id")
	private Integer shopAssistantId;

	@NotNull(message = "Book product is required")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "isbn", nullable = false)
	private BookProduct bookProduct;

	@Positive(message = "Quantity must be greater than 0")
	@Column(name = "quantity", nullable = false)
	private Short quantity;
}
