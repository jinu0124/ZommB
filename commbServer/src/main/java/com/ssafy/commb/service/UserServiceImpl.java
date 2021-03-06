package com.ssafy.commb.service;

import com.ssafy.commb.dao.UserDao;
import com.ssafy.commb.dto.encode.Encoder;
import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.dto.user.UserDto;
import com.ssafy.commb.dto.user.level.LevelDto;
import com.ssafy.commb.exception.ApplicationException;
import com.ssafy.commb.model.ConfirmationToken;
import com.ssafy.commb.model.User;
import com.ssafy.commb.repository.ConfirmationTokenRepository;
import com.ssafy.commb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    @Value("${cloud.profile}")
    private String awsProfileUrl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDao userDao;

    //  Bcrypt는 패스워드를 해싱할 때 내부적으로 랜덤한 솔트를 생성하기 때문에 같은 문자열에 대해서 다른 인코드된 결과를 반환
    private final String ENCODE_ID = "bcrypt";
    private static final Map<String, PasswordEncoder> encoders = Encoder.getEncoder();

    @Override
    public UserDto.ResponseList getUsers(String nickname) {
        UserDto.ResponseList retRes = new UserDto.ResponseList();
        Optional<List<User>> users = userRepository.findByNicknameStartsWith(nickname);

        users.ifPresent(selectUser -> {
            List<UserDto> userResList = new ArrayList<>();

            for(User user : selectUser){
                LevelDto level = LevelDto.builder().bookmark(user.getBookmark()).bookmarkOn(user.getBookmarkOn() != 0).pencil(user.getPencil())
                        .pencilOn(user.getPencilOn() != 0).build();

                UserDto userDto = UserDto.builder().id(user.getId()).email(user.getEmail()).name(user.getName()).nickname(user.getNickname())
                        .role(user.getRole()).userFileUrl(user.getFileUrl()).level(level).build();

                userResList.add(userDto);
            }

            retRes.setData(userResList);
        });

        return retRes;
    }

    public int joinUser(MyDto.Request myReq) {
        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(ENCODE_ID, encoders);
        String encPassword = passwordEncoder.encode(myReq.getPassword());
        if(!passwordEncoder.matches(myReq.getPassword(), encPassword)) throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "비밀번호 암호화 중 불일치 오류");

        User user = new User(myReq.getEmail(), encPassword, myReq.getName(), myReq.getNickname());

        return userRepository.save(user).getId();
    }

    public boolean isExistEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public MyDto.Response login(MyDto.LoginRequest myReq) {
        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(ENCODE_ID, encoders);

        Optional<User> user = userRepository.findByEmail(myReq.getEmail());
        if (!user.isPresent()) throw new ApplicationException(HttpStatus.valueOf(401), "일치하는 회원 정보가 없습니다.");
        if(!passwordEncoder.matches(myReq.getPassword(), user.get().getPassword())) throw new ApplicationException(HttpStatus.valueOf(401), "일치하는 회원 정보가 없습니다.");

        System.out.println(awsProfileUrl);
        MyDto my = new MyDto();
        my.setId(user.get().getId());
        my.setNickname(user.get().getNickname());
        my.setUserFileUrl(user.get().getFileUrl() != null ? (awsProfileUrl + user.get().getFileUrl()) : null);

        MyDto.Response myRes = new MyDto.Response();
        myRes.setData(my);
        if(user.get().getRole() == null) throw new ApplicationException(HttpStatus.valueOf(403), "이메일 인증 필요", my);

        return myRes;
    }

    public MyDto.Response socialLogin(int userId){
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) throw new ApplicationException(HttpStatus.valueOf(401), "로그인 실패");

        System.out.println(awsProfileUrl);
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
    public String TokenGeneration(int userId, String receiverEmail, String url){
        return confirmationTokenService.createEmailConfirmationToken(userId, receiverEmail, url);        // 유저 ID, 이메일로 토큰 발행 및 이메일 발송 / DB에 토큰 정보 저장
    }

    // 이메일 인증
    public boolean confirmEmail(String key) {
        Optional<ConfirmationToken> findConfirmationToken = confirmationTokenService.findByIdAndExpirationDateAfterAndExpired(key); // token

        AtomicBoolean ret = new AtomicBoolean(false);

        findConfirmationToken.ifPresent(select -> {
            Optional<User> user = userRepository.findById(select.getUserId());    // 유저의 Role 변경 로직

            user.ifPresent(userSelect -> {
                userSelect.setRole("USR");
                userRepository.save(userSelect);
            });
            ret.set(true);
            select.useToken();
            confirmationTokenRepository.delete(select);
        });

        Optional<ConfirmationToken> findExpToken = confirmationTokenRepository.findById(key);
        findExpToken.ifPresent(confirmationTokenRepository::delete);

        return ret.get();
    }

    @Override
    public boolean confirmEmailForPassword(String key) {
        Optional<ConfirmationToken> findConfirmationToken = confirmationTokenService.findByIdAndExpirationDateAfterAndExpired(key); // token

        return findConfirmationToken.isPresent();
    }

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

    public void validatePassword( String password){
         String pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}$";
         if(!password.matches(pattern)) throw new ApplicationException(HttpStatus.valueOf(400), "비밀번호 형식 오류");
    }

    @Override
    public void deleteUser(int userId) {
        Optional<User> user = userRepository.findUserById(userId);
        if(!user.isPresent()) return;

        userRepository.deleteById(userId);
    }

    @Override
    public UserDto.Response getUserInfo(int userId, HttpServletRequest request) {
        UserDto user = userDao.userInfo(userId, (int) request.getAttribute("userId"));
        UserDto.Response userRes = new UserDto.Response();
        userRes.setData(user);

        return userRes;
    }

    @Override
    public UserDto.ResponseList followRecommend(HttpServletRequest request) {
        List<UserDto> users = userDao.getMyFollowerExFollowing( (int) request.getAttribute("userId"));
        UserDto.ResponseList userResList = new UserDto.ResponseList();
        userResList.setData(users);
        return userResList;
    }

    @Override
    public int getUserInfoByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) return user.get().getId();
        throw new ApplicationException(HttpStatus.valueOf(401), "존재하지 않는 이메일 입니다.");
    }

}



/*
다중 word 검색

Set<Feeds> feeds <= HashTag 들
for(HashTag)
  feeds




 */