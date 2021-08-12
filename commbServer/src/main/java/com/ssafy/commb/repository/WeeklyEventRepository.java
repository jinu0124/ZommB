package com.ssafy.commb.repository;

import com.ssafy.commb.model.WeeklyEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyEventRepository extends JpaRepository<WeeklyEvent, Integer> {
}
