package com.bookshop.oz.openlibrary.model;

import java.util.List;

import lombok.Data;

@Data
public class Editions {
	private List<Edition> docs;

	public Edition getDocs() {
		return docs.stream().findFirst().orElseThrow();
	}
}
