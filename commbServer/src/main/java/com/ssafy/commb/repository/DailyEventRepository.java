package com.ssafy.commb.repository;

import com.ssafy.commb.model.DailyEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DailyEventRepository extends JpaRepository<DailyEvent, Integer> {

    List<DailyEvent> findAllByCreateAtBetween(Date startDate, Date endDate);
}
