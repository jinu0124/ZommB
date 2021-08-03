package com.ssafy.commb.repository;

import com.ssafy.commb.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Integer> {

    Optional<Feed> findById(int id);
}
