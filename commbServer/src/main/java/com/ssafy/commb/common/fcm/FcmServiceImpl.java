package com.ssafy.commb.common.fcm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.commb.dto.fcm.FcmDto;
import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.dto.user.UserDto;
import com.ssafy.commb.model.FirebaseToken;
import com.ssafy.commb.model.User;
import com.ssafy.commb.repository.FirebaseTokenRepository;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.UnknownServiceException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService{

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/commb-43e85/messages:send";

    @Autowired
    private FcmInitializer fcmInitializer;

    @Autowired
    private FirebaseTokenRepository firebaseTokenRepository;

    /**
     * Firebase 서버에 Push 알림 발송 요청
     * @param targetToken : 타겟 유저 Firebase Token
     * @param title : 발신 제목
     * @param body : 발신 내용
     * @throws IOException
     */
    @Override
    public void send(String targetToken, String title, String body) throws IOException {

//        Message message = Message.builder()
//                .setToken(targetToken)
//                .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
//                        .setNotification(new WebpushNotification(title, body))
//                        .build())
//                .build();
//
//        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
////        System.out.println(FirebaseMessaging.getInstance().toString());
//        System.out.println(response);

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody
                .create(makeMessage(targetToken, title, body), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + fcmInitializer.getFcmAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        System.out.println(response.body().string());
        System.out.println(response.headers());
    }

    /**
     * 로그인 시 DB에 Firebase 연동 토큰 저장
     * @param myRes : 유저 정보
     * @param token : Firebase 연동 토큰
     */
    @Override
    public void save(MyDto.Response myRes, String token) {
        if (firebaseTokenRepository.findByUserIdAndToken(myRes.getData().getId(), token).isPresent()) return;

        User user = new User();
        user.setId(myRes.getData().getId());
        FirebaseToken firebaseToken = FirebaseToken.builder()
                .user(user)
                .token(token)
                .build();

        firebaseTokenRepository.save(firebaseToken);
    }

    /**
     * 특정 유저의 Firebase Auth Token 들 가져오기 -> 사용자가 여러 브라우저로 접속했을 때 모두 캐스팅을 위해
     * @param userId : 유저 ID
     * @return : Firebase 인증 토큰
     */
    @Override
    public List<FirebaseToken> getUserToken(int userId) {
        Optional<List<FirebaseToken>> firebaseTokens = firebaseTokenRepository.findByUserId(userId);

        return firebaseTokens.orElse(null);
    }

    /**
     * 여러 유저에게 캐스팅을 위해 Firebase Auth Token 들 가져오기
     * @param users  : UserDto 리스트
     * @return : Firebase 인증 토큰
     */
    @Override
    public List<FirebaseToken> getUsersToken(List<UserDto> users) {
        List<Integer> userIds = new ArrayList<>();
        for(UserDto user : users) userIds.add(user.getId());
        Optional<List<FirebaseToken>> firebaseTokens = firebaseTokenRepository.findByUserIdIn(userIds);

        return firebaseTokens.orElse(null);
    }

    /**
     * 현재 접속해 있는 모든 사용자에게 캐스팅
     * @return :  Firebase 인증 토큰
     */
    @Override
    public List<FirebaseToken> getBroadcastToken() {
        List<FirebaseToken> firebaseTokens = firebaseTokenRepository.findAll();

        if(firebaseTokens.size() == 0) return null;
        return firebaseTokens;
    }

    /**
     * 로그아웃 시 해당 브라우저 연동 Firebase 토큰 DB에서 삭제
     * @param token : 토큰
     */
    @Override
    public void del(String token) {
        Optional<FirebaseToken> firebaseToken = firebaseTokenRepository.findByToken(token);

        firebaseToken.ifPresent(select -> {
            firebaseTokenRepository.delete(select);
        });
    }

    /**
     * Firebase로 REST 전송을 위한 메시지 만들기
     * @param targetToken : 클라이언트마다의 Firebase Token
     * @param title : 발신 제목
     * @param body : 발신 내용
     * @return : String(메시지), FcmDto
     * @throws : JsonProcessingException
     */
    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(FcmDto.builder()
                                        .message(FcmDto.Message.builder()
                                                    .token(targetToken)
                                                    .notification(FcmDto.Notification.builder()
                                                                                    .title(title)
                                                                                    .body(body)
                                                                                    .build())
                                                    .build()
                                        ).validate_only(false)
                                        .build());

    }


}
