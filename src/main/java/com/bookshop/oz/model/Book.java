package com.bookshop.oz.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table (name = "book")
@Data
@Component
@Accessors (chain = true)
// @Scope (scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Book {
	@Id
    @Column(name = "isbn", length = 13, nullable = false, unique = true)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "language", length = 3, nullable = false)
    private String language;

    @Column(name = "cover_i", nullable = false)
    private Integer coverID;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
