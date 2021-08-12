package com.ssafy.commb.repository;

import com.ssafy.commb.model.Book;
import com.ssafy.commb.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Integer> {

    Optional<Keyword> findByKeyword(String keyword);
}
