package com.ssafy.commb.repository;

import com.ssafy.commb.model.DailyEvent;
import com.ssafy.commb.model.DailyEventParticipate;
import com.ssafy.commb.model.DailyEventParticipateId;
import com.ssafy.commb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DailyEventParticipateRepository extends JpaRepository<DailyEventParticipate, DailyEventParticipateId> {

    Optional<DailyEventParticipate> findByDailyEventAndUser(DailyEvent dailyEvent, User user);
}
