package com.bookshop.oz.openlibrary.model;

import java.util.List;

import lombok.Data;

@Data
public class Editions {
	private int numFound;
    private int start;
    private boolean numFoundExact;
    private List<Edition> docs;
}
