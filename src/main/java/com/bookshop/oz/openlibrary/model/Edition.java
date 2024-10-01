package com.bookshop.oz.openlibrary.model;

import java.util.List;

import lombok.Data;

@Data
public class Edition {
	private List<String> publisher;
    private List<String> isbn;
    private List<String> language;
}
