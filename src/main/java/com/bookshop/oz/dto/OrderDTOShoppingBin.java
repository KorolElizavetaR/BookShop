package com.bookshop.oz.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.model.LocationPoint;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.model.enumeration.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO модель для /shopping_bin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTOShoppingBin {
	private Long orderId;
	private LocationPoint location;
	private LocalDateTime createdAt;
	private BookProduct bookProduct;
	private Integer quantity;
}
