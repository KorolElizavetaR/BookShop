package com.bookshop.oz.model.pk;

import java.io.Serializable;

import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.model.LocationPoint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockId implements Serializable {
    private LocationPoint location;  
    private BookProduct bookProduct; 
}
