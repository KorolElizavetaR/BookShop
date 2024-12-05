package com.bookshop.oz.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.bookshop.oz.model.Book;
import com.bookshop.oz.model.BookProduct;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Все, что не может сделать JPA, сделает DAO
 */
@Repository
public class BookProductDao {
	@PersistenceContext
	private EntityManager entityManager;

	public List<BookProduct> getItems(String order, String like) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BookProduct> query = cb.createQuery(BookProduct.class);
		Root<BookProduct> root = query.from(BookProduct.class);
		Join<BookProduct, Book> bookJoin = root.join("book", JoinType.LEFT);
		if (like != null && !like.isBlank()) {
			Predicate likePredicate = cb.like(bookJoin.get("title"), "%" + like + "%");
			query.where(likePredicate);
		}
		if (order != null && !order.isBlank()) {
			switch (order) {
			case "asc":
				query.orderBy(cb.asc(root.get("price")));
				break;
			case "desc":
				query.orderBy(cb.desc(root.get("price")));
				break;
			}
		}
		TypedQuery<BookProduct> typedQuery = entityManager.createQuery(query);
		return typedQuery.getResultList();
	}
}
