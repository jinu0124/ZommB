package com.ssafy.commb.common.fcm;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.ssafy.commb.dto.fcm.FcmDto;
import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.dto.user.UserDto;
import com.ssafy.commb.model.FirebaseToken;

import java.io.IOException;
import java.util.List;

public interface FcmService {
    public void send(FcmDto fcm) throws InterruptedException, IOException;

    public void sends(List<FirebaseToken> tokens, FcmDto fcm) throws InterruptedException, IOException, FirebaseMessagingException;

    public void save(MyDto.Response myRes, String token);

    public List<FirebaseToken> getUserToken(int userId);

    public List<FirebaseToken> getUsersToken(List<UserDto> users);

    public List<FirebaseToken> getBroadcastToken();

    public void del(String token);                                                 // 브라우저마다 각각 로그인 접속해 있는 상태일 수 있기에 각 브라우저별로 토큰 관리

    public void deleteLastDay();

    public void savePushAlarm(FcmDto fcm);
}
