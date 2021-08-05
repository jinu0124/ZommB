package com.ssafy.commb.repository;

import com.ssafy.commb.model.Feed;
import com.ssafy.commb.model.Thumb;
import com.ssafy.commb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThumbRepository extends JpaRepository<Thumb, Integer> {

    Optional<Thumb> findByFeedAndUser(Feed feed, User user);
}
