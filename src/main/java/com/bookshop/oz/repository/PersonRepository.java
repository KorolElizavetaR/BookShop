package com.bookshop.oz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookshop.oz.model.Person;
import com.bookshop.oz.model.enumeration.Authority;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
	Optional<Person> findByEmail(String email);

	Optional<Person> findByPhone(String phone);

	@Query("SELECT p FROM Person p WHERE :excludedRole NOT IN (SELECT a FROM p.autorities a)")
	List<Person> findAllWithoutAuthority(@Param("excludedRole") Authority excludedRole);
}
