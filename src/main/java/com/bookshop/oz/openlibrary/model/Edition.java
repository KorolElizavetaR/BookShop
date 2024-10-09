package com.bookshop.oz.openlibrary.model;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Edition {
	private List<String> publisher;
    private List<String> isbn;
    private List<String> language;
    
    public String getIsbn13()
    {
    	if (Optional.ofNullable(isbn).isEmpty()) throw new NoSuchElementException();
    	return isbn.stream().filter(isbn -> isbn.length() == 13).findFirst().orElseThrow();
    }
    
    public String getPublisher()
    {
    	return publisher.stream().findFirst().orElseThrow();
    }
    
    public String getLanguage()
    {
    	return Optional.ofNullable(language)
    	        .map(List::stream)
    	        .flatMap(Stream::findFirst) 
    	        .orElseThrow(); 
    }
}
