package com.ssafy.commb.repository;

import com.ssafy.commb.model.WeeklyEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface WeeklyEventRepository extends JpaRepository<WeeklyEvent, Integer> {

    Optional<WeeklyEvent> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date startDate, Date endDate);
}
