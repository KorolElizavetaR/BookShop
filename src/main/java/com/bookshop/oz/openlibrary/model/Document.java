package com.bookshop.oz.openlibrary.model;

import java.util.List;

import lombok.Data;

@Data
public class Document {
	private List<String> author_name;
    private String key;
    private String title;
    private Editions editions;
    private Integer cover_i;
}
