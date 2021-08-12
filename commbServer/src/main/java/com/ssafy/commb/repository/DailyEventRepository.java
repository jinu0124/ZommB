package com.ssafy.commb.repository;

import com.ssafy.commb.model.DailyEvent;
import com.ssafy.commb.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyEventRepository extends JpaRepository<DailyEvent, Integer> {

}
