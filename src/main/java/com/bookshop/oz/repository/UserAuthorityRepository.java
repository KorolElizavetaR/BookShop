package com.bookshop.oz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookshop.oz.model.Book;
import com.bookshop.oz.model.UserAuthority;
import com.bookshop.oz.model.pk.UserAuthorityId;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, UserAuthorityId>{

}
