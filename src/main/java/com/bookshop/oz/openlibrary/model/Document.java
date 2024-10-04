package com.bookshop.oz.openlibrary.model;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import lombok.Data;

@Data
public class Document {
	
	private List<String> author_name;
    private Integer cover_i;
    private String title;
    private List<String> subject;
    private Editions editions;
    
    public String getAuthorName()
    {
    	return author_name.stream().findFirst().orElseThrow();
    }
    
    public String getTitle()
    {
    	return Optional.of(title).orElseThrow();
    }
    
    public Integer getCover_i()
    {
    	return Optional.of(cover_i).orElseThrow();
    }
    
    public String getSubject()
    {
    	return subject.stream().reduce((res, subj) -> res+=subj + ", ").get();
    }
}
