package com.ssafy.commb.dao;

import com.ssafy.commb.dto.fcm.FcmDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PushAlarmDao {

    void save(FcmDto fcm);

    List<FcmDto> getNewAlarm(Integer page, int userId);

    List<FcmDto> getAllAlarm(Integer page, int userId);

    void updateIsRead(Integer userId);
}
