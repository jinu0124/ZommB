package com.ssafy.commb.common.fcm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.*;
import com.ssafy.commb.dao.PushAlarmDao;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService{

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/commbfcm/messages:send";

    @Autowired
    private FcmInitializer fcmInitializer;

    @Autowired
    private FirebaseTokenRepository firebaseTokenRepository;

    @Autowired
    private PushAlarmDao pushAlarmDao;

    // HTTP V1, Firebase Admin SDK 모두 엣지 브라우저로 푸시 요청 시 파이어베이스 서버 INTERNAL 에러 발생 -> 일단 크롬으로 진행
    /**
     * Firebase 서버에 Push 알림 1:1 발송 요청(가장 최근 접속한 클라이언트 브라우저) : FcmDto 데이터 모두 발송(notification, data)
     * @Param : FcmDto
     * @throws IOException
     */
    @Override
    public void send(FcmDto fcm) throws IOException {
        if(fcm.getMessage().getToken() == null) return;

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = makeRequest(makeMessage(fcm));

        Response response = okHttpClient.newCall(request).execute();
        System.out.println(response.body().string());
    }

    /**
     * Firebase 서버에 Push 알림 1:다 발송 요청 : FcmDto 데이터 모두 발송
     * @param tokens : 수신받을 클라이언트의 토큰 리스트
     * @param fcm : FcmDto
     * @throws InterruptedException
     * @throws IOException
     * @throws FirebaseMessagingException
     */
    @Override
    public void sends(List<FirebaseToken> tokens, FcmDto fcm) throws InterruptedException, IOException, FirebaseMessagingException {
        if(tokens.size() == 0) return;

        MulticastMessage multicastMessage = makeMulticastMessage(tokens, fcm);

        BatchResponse response = FirebaseMessaging.getInstance()
                .sendMulticast(multicastMessage);

        if (response.getFailureCount() > 0) {
            List<SendResponse> responses = response.getResponses();
            List<String> failedTokens = new ArrayList<>();
            for (int i = 0; i < responses.size(); i++) {
                if (!responses.get(i).isSuccessful()) {
                    System.out.println(responses.get(i).getException());
                    failedTokens.add(tokens.get(i).getToken());
                }
            }
            System.out.println("List of tokens that caused failures: " + failedTokens);
        }
    }

    /**
     * 로그인 시 DB에 Firebase 연동 토큰 저장
     * @param myRes : 유저 정보
     * @param token : Firebase 연동 토큰
     */
    @Override
    public void save(MyDto.Response myRes, String token) {
        Optional<List<FirebaseToken>> firebaseTokens = firebaseTokenRepository.findByUserIdAndToken(myRes.getData().getId(), token);
        if(firebaseTokens.isPresent() && firebaseTokens.get().size() >= 1) return;

        User user = new User();
        user.setId(myRes.getData().getId());
        FirebaseToken firebaseToken = FirebaseToken.builder()
                .user(user)
                .token(token)
                .createAt(LocalDateTime.now(ZoneId.of("+9")))
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
     * 여러 유저에게 캐스팅을 위해 Firebase Auth Token 들 가져오기 (최근 접속한 브라우저 순으로 정렬하여 반환)
     * @param users  : UserDto 리스트
     * @return : Firebase 인증 토큰
     */
    @Override
    public List<FirebaseToken> getUsersToken(List<UserDto> users) {
        List<Integer> userIds = new ArrayList<>();
        for(UserDto user : users) userIds.add(user.getId());
        Optional<List<FirebaseToken>> firebaseTokens = firebaseTokenRepository.findByUserIdInOrderByCreateAtDesc(userIds);

        return firebaseTokens.orElse(null);
    }

    /**
     * 현재 접속해 있는 모든 사용자에게 캐스팅을 위해
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
        Optional<List<FirebaseToken>> firebaseTokens = firebaseTokenRepository.findByToken(token);

        firebaseTokens.ifPresent(select -> {
            firebaseTokenRepository.deleteAll(select);
        });
    }

    /**
     * 하루 전까지의 Trash Firebase Token 정보 삭제하기 (알림 관련)
     */
    @Override
    public void deleteLastDay() {
        Optional<List<FirebaseToken>> firebaseTokens = firebaseTokenRepository.findByCreateAtBefore(LocalDateTime.now(ZoneId.of("+9")).minusDays(1));

        firebaseTokens.ifPresent(select ->{
            firebaseTokenRepository.deleteAll(firebaseTokens.get());
        });
    }

    @Override
    public void savePushAlarm(FcmDto fcm) {

        pushAlarmDao.save(fcm);
    }

    /**
     * Firebase로 REST 전송을 위한 메시지 만들기
     *
     * @return : String(메시지), FcmDto
     * @throws : JsonProcessingException
     */
    private String makeMessage(FcmDto fcm) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(fcm);
    }

    /**
     * Firebase로 REST 전송을 위한 Multicast 메시지 준비하기, null 데이터는 ""로 치환
     * @param tokens : 수신자 클라이언트의 Registration Tokens
     * @param fcm : FcmDto data
     * @return : Multicast Message
     */
    private MulticastMessage makeMulticastMessage(List<FirebaseToken> tokens, FcmDto fcm){
        List<String> tokenList = new ArrayList<>();
        for(FirebaseToken token : tokens) tokenList.add(token.getToken());
        System.out.println(fcm.getMessage().getNotification().getTitle());
        System.out.println(fcm.getMessage().getData().getFeedFileUrl());
        return MulticastMessage.builder()
                .setNotification(new Notification(fcm.getMessage().getNotification().getTitle(), fcm.getMessage().getNotification().getBody()))
                .putData("nickname", String.valueOf(fcm.getMessage().getData().getNickname() == null ? "" : fcm.getMessage().getData().getNickname() ))
                .putData("userId", String.valueOf(fcm.getMessage().getData().getUserId() == null ? "" : fcm.getMessage().getData().getUserId() ))
                .putData("userFileUrl", String.valueOf(fcm.getMessage().getData().getUserFileUrl() == null ? "" : fcm.getMessage().getData().getUserFileUrl() ))
                .putData("feedId", String.valueOf(fcm.getMessage().getData().getFeedId() == null ? "" : fcm.getMessage().getData().getFeedId() ))
                .putData("feedFileUrl", String.valueOf(fcm.getMessage().getData().getFeedFileUrl() == null ? "" : fcm.getMessage().getData().getFeedFileUrl() ))
                .putData("commentId", String.valueOf(fcm.getMessage().getData().getCommentId() == null ? "" : fcm.getMessage().getData().getCommentId() ))
                .putData("comment", String.valueOf(fcm.getMessage().getData().getComment() == null ? "" : fcm.getMessage().getData().getComment() ))
                .putData("content", String.valueOf(fcm.getMessage().getData().getContent() == null ? "" : fcm.getMessage().getData().getContent()))
                .putData("createAt", String.valueOf(fcm.getMessage().getData().getCreateAt() == null ? "" : fcm.getMessage().getData().getCreateAt()))
                .addAllTokens(tokenList)
                .build();
    }

    /**
     * OkHttp Request 준비하기
     * @param message : 메시지 : Client Registration Token, FcmDto data
     * @return : Request
     */
    private Request makeRequest(String message){
        RequestBody requestBody = RequestBody
                .create(message, MediaType.get("application/json; charset=utf-8"));

        return new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + fcmInitializer.getFcmAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();
    }
}
