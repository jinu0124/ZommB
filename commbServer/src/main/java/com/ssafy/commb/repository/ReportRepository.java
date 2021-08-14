package com.ssafy.commb.repository;

import com.ssafy.commb.model.Feed;
import com.ssafy.commb.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    Optional<List<Feed>> findByFeedId(int feedId);
}
