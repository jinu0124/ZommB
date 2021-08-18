package com.ssafy.commb.repository;

import com.ssafy.commb.model.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Integer> {

    List<HashTag> findByFeedId(int feedId);

    Optional<HashTag> findByTagAndFeedId(String tag, int feedId);
}
