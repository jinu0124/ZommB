package com.ssafy.commb.service;

import com.amazonaws.services.dynamodbv2.xspec.M;
import com.querydsl.core.Tuple;
import com.querydsl.core.support.QueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.commb.dao.PushAlarmDao;
import com.ssafy.commb.dao.UserDao;
import com.ssafy.commb.dto.encode.Encoder;
import com.ssafy.commb.dto.fcm.FcmDto;
import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.dto.user.UserDto;
import com.ssafy.commb.dto.user.level.LevelDto;
import com.ssafy.commb.exception.ApplicationException;
import com.ssafy.commb.model.ConfirmationToken;
import com.ssafy.commb.model.QBookShelves;
import com.ssafy.commb.model.QUser;
import com.ssafy.commb.model.User;
import com.ssafy.commb.model.follow.Follow;
import com.ssafy.commb.repository.ConfirmationTokenRepository;
import com.ssafy.commb.repository.FirebaseTokenRepository;
import com.ssafy.commb.repository.FollowRepository;
import com.ssafy.commb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PushAlarmDao pushAlarmDao;

    @PersistenceContext
    private EntityManager entityManager;

    //  Bcrypt는 패스워드를 해싱할 때 내부적으로 랜덤한 솔트를 생성하기 때문에 같은 문자열에 대해서 다른 인코딩 결과를 반환
    private final String ENCODE_ID = "bcrypt";
    private static final Map<String, PasswordEncoder> encoders = Encoder.getEncoder();      // 인코더 : 여러 타입의 암호화 방식을 저장

    /**
     * 닉네임으로 유저 검색
     * @param nickname : 닉네임
     * @return : 검색된 유저 정보 리스트
     */
    @Override
    public UserDto.ResponseList getUsers(String nickname, int page) {
        UserDto.ResponseList retRes = new UserDto.ResponseList();
        JPAQueryFactory query = new JPAQueryFactory(entityManager);
        QUser qUser = QUser.user;

        JPAQuery<Tuple> ret = query.from(qUser)
                .where(qUser.nickname.startsWith(nickname))
                .orderBy(qUser.nickname.length().asc())
                .offset(page)
                .limit(50)
                .select(qUser.nickname, qUser.id, qUser.name, qUser.bookmark, qUser.role, qUser.bookmarkOn, qUser.email, qUser.pencil, qUser.pencilOn, qUser.fileUrl);

        List<Tuple> users = ret.fetch();
        List<UserDto> userResList = new ArrayList<>();

        if(users.size() == 0) throw new ApplicationException(HttpStatus.valueOf(204), "end of page");

        for(Tuple user : users){
            LevelDto level = LevelDto.builder()
                    .bookmark(user.get(qUser.bookmark))
                    .bookmarkOn(user.get(qUser.bookmarkOn) == 1)
                    .pencil(user.get(qUser.pencil))
                    .pencilOn(user.get(qUser.pencilOn) != 0)
                    .build();

            UserDto userDto = UserDto.builder()
                    .id(user.get(qUser.id))
                    .email(user.get(qUser.email))
                    .name(user.get(qUser.name))
                    .nickname(user.get(qUser.nickname))
                    .role(user.get(qUser.role))
                    .userFileUrl(user.get(qUser.fileUrl))
                    .level(level)
                    .build();

            userResList.add(userDto);
        }

        retRes.setData(userResList);
        return retRes;
    }

    /**
     * 유저 회원 등록
     * @param myReq : 등록 정보
     * @return : 유저 ID
     */
    public int joinUser(MyDto.Request myReq) {
        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(ENCODE_ID, encoders);
        String encPassword = passwordEncoder.encode(myReq.getPassword());
        if(!passwordEncoder.matches(myReq.getPassword(), encPassword)) throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "비밀번호 암호화 중 불일치 오류");

        User user = new User(myReq.getEmail(), encPassword, myReq.getName(), myReq.getNickname());

        return userRepository.save(user).getId();
    }

    /**
     * wndqhr 이메일 체크
     * @param email : 이메일
     * @return : boolean
     */
    public boolean isExistEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    /**
     * 로그인
     * @param myReq
     * @return
     */
    public MyDto.Response login(MyDto.LoginRequest myReq) {
        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(ENCODE_ID, encoders);

        Optional<User> user = userRepository.findByEmail(myReq.getEmail());
        if (!user.isPresent()) throw new ApplicationException(HttpStatus.valueOf(401), "일치하는 회원 정보가 없습니다.");
        if(!passwordEncoder.matches(myReq.getPassword(), user.get().getPassword())) throw new ApplicationException(HttpStatus.valueOf(401), "일치하는 회원 정보가 없습니다.");

        MyDto my = new MyDto();
        my.setId(user.get().getId());
        my.setNickname(user.get().getNickname());
        my.setUserFileUrl(user.get().getFileUrl());

        MyDto.Response myRes = new MyDto.Response();
        myRes.setData(my);
        if(user.get().getRole() == null) throw new ApplicationException(HttpStatus.valueOf(403), "이메일 인증 필요", my);

        return myRes;
    }

    /**
     * 소셜 카카오 로그인
     * @param userId
     * @return
     */
    public MyDto.Response socialLogin(int userId){
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) throw new ApplicationException(HttpStatus.valueOf(401), "로그인 실패");

        MyDto my = new MyDto();
        my.setId(user.get().getId());
        my.setNickname(user.get().getNickname());
        my.setUserFileUrl(user.get().getFileUrl() != null ? (user.get().getFileUrl()) : "");

        MyDto.Response myRes = new MyDto.Response();
        myRes.setData(my);
        if(user.get().getRole() == null) throw new ApplicationException(HttpStatus.valueOf(403), "이메일 인증 필요", my);

        return myRes;
    }



    private final ConfirmationTokenService confirmationTokenService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    // 토큰 생성 & 메일 발송 & Redis query 저장

    /**
     * 메일 토큰 생성 * 발송 * Token 저장
     * @param userId : 유저 ID
     * @param receiverEmail : Email 수신지
     * @param url : Redirect URL
     * @return
     */
    public String TokenGeneration(int userId, String receiverEmail, String url){
        return confirmationTokenService.createEmailConfirmationToken(userId, receiverEmail, url);
    }

    /**
     * 이메일 인증 및 정회원 전환
     * @param key : Token Key 값
     * @return : boolean
     */
    public boolean confirmEmail(String key) {
        Optional<ConfirmationToken> findConfirmationToken = confirmationTokenService.findByIdAndExpirationDateAfterAndExpired(key); // token

        AtomicBoolean ret = new AtomicBoolean(false);

        findConfirmationToken.ifPresent(select -> {
            Optional<User> user = userRepository.findById(select.getUserId());                                               // 유저의 Role 변경 로직

            user.ifPresent(userSelect -> {
                userSelect.setRole("USR");
                userRepository.save(userSelect);
            });
            ret.set(true);
            select.useToken();
        });

        Optional<ConfirmationToken> findExpToken = confirmationTokenRepository.findById(key);
        findExpToken.ifPresent(confirmationTokenRepository::delete);

        return ret.get();
    }

    /**
     * 패스워드 초기화를 위한 이메일 인증
     * @param key : Token Key
     * @return : boolean
     */
    @Override
    public boolean confirmEmailForPassword(String key) {
        Optional<ConfirmationToken> findConfirmationToken = confirmationTokenService.findByIdAndExpirationDateAfterAndExpired(key); // token

        return findConfirmationToken.isPresent();
    }

    /**
     * 패스워드 변경
     * @param userReq : 기존 비밀번호, 새 비밀번호
     * @param request : 유저 ID
     */
    @Override
    public void updatePassword(UserDto.ModifyPwRequest userReq, HttpServletRequest request) {
        Optional<User> user = userRepository.findById((int) request.getAttribute("userId"));

        if(!user.isPresent()) throw new ApplicationException(HttpStatus.valueOf(401), "회원 정보가 없습니다.");

        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(ENCODE_ID, encoders);
        if(!passwordEncoder.matches(userReq.getOldPassword(), user.get().getPassword())) throw new ApplicationException(HttpStatus.valueOf(401), "비밀번호 불일치");

        user.ifPresent(userSelect -> {
            userSelect.setPassword(passwordEncoder.encode(userReq.getNewPassword()));
            userRepository.save(userSelect);
        });
    }

    /**
     * 비밀번호 초기화를 통한 비밀번호 변경
     * @param userId : 유저 ID
     * @param password : 새 비밀번호
     * @param tmp : Overloading
     */
    @Override
    public void updatePassword(int userId, String password, int tmp) {
        Optional<ConfirmationToken> confirmation = confirmationTokenRepository.findByUserId(userId);

        confirmation.ifPresent(select -> {
            userRepository.delete(select.getUser());
        });

        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) throw new ApplicationException(HttpStatus.valueOf(401), "회원 정보를 찾을 수 없습니다.");

        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(ENCODE_ID, encoders);
        user.ifPresent(selectUser -> {
            selectUser.setPassword(passwordEncoder.encode(password));
            userRepository.save(selectUser);
        });
    }

    /**
     * 비밀번호 유효성 검증
     * @param password : pw
     */
    public void validatePassword( String password){
         String pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}$";
         if(!password.matches(pattern)) throw new ApplicationException(HttpStatus.valueOf(400), "비밀번호 형식 오류");
    }

    /**
     * 회원 탈퇴
     * @param userId : 유저 ID
     */
    @Override
    public void deleteUser(int userId) {
        Optional<User> user = userRepository.findUserById(userId);
        if(!user.isPresent()) return;

        userRepository.deleteById(userId);
    }

    /**
     * 유저 정보
     * @param userId : target 유저 ID
     * @param request : 본인 유저 ID
     * @return : 유저 정보
     */
    @Override
    public UserDto.Response getUserInfo(int userId, HttpServletRequest request) {
        UserDto user = userDao.userInfo(userId, (int) request.getAttribute("userId"));
        UserDto.Response userRes = new UserDto.Response();
        userRes.setData(user);

        return userRes;
    }

    /**
     * 팔로우 추천
     * @param request : 유저 ID
     * @return 추천 팔로우 목록 반환
     */
    @Override
    public UserDto.ResponseList followRecommend(int page, HttpServletRequest request) {
        List<UserDto> users = userDao.getMyFollowerExFollowing( page, (int) request.getAttribute("userId"));

        if(users.size() == 0) throw new ApplicationException(HttpStatus.valueOf(204), "end of page");

        UserDto.ResponseList userResList = new UserDto.ResponseList();
        userResList.setData(users);
        return userResList;
    }

    /**
     * 이메일로 유저 ID 반환
     * @param email : Email
     * @return : 유저 ID
     */
    @Override
    public int getUserInfoByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) return user.get().getId();
        throw new ApplicationException(HttpStatus.valueOf(401), "존재하지 않는 이메일 입니다.");
    }


    /**
     * 유저 권한 받기
     * @param userId : 유저 ID
     * @return : 권한
     */
    @Override
    public String getUserRole(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) throw new ApplicationException(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자");

        return user.get().getRole();
    }

    @Override
    public List<FcmDto> getAlarms(Integer page, HttpServletRequest request) {

        List<FcmDto> fcms = pushAlarmDao.getNewAlarm(page, (int) request.getAttribute("userId"));
        if(fcms.size() >= 1) pushAlarmDao.updateIsRead((int) request.getAttribute("userId"));

        return fcms;
    }

    @Override
    public List<FcmDto> getAllAlarms(Integer page, HttpServletRequest request) {

        List<FcmDto> fcms = pushAlarmDao.getAllAlarm(page, (int) request.getAttribute("userId"));
        if(fcms.size() >= 1) pushAlarmDao.updateIsRead((int) request.getAttribute("userId"));

        return fcms;
    }

    /**
     * 일반회원이 유저 검색
     * @param nickname : 검색 닉네임
     * @param page : 페이징
     * @param request : 유저 ID
     * @return
     */
    @Override
    public MyDto.ResponseList getUsers(String nickname, int page, HttpServletRequest request) {
        List<MyDto> my = userDao.getUsers(nickname, String.valueOf(page), String.valueOf((int) request.getAttribute("userId")));

        if(my.size() == 0) throw new ApplicationException(HttpStatus.valueOf(204), "end of page");

        return MyDto.ResponseList.builder()
                .data(my)
                .build();
    }
}
