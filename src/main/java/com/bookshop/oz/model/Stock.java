package com.bookshop.oz.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.IdClass;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock")
@IdClass(StockId.class)
public class Stock { 
	@Id
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id", nullable = false)
    @NotNull(message = "Location ID cannot be null")
    private LocationPoint location;

    @Id
    @ManyToOne
    @JoinColumn(name = "isbn", referencedColumnName = "isbn", nullable = false)
    @NotNull(message = "ISBN cannot be null")
    private BookProduct bookProduct;

    @NotNull(message = "Quantity cannot be null")
    @Positive
    @Column(name = "quantity", nullable = false)
    private Short quantity;
}


@Data
class StockId implements Serializable {
    private LocationPoint location;  // Match the name and type in Stock
    private BookProduct bookProduct; // Match the name and type in Stock
}
