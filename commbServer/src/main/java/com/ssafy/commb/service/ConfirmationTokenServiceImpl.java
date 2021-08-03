package com.ssafy.commb.service;

import com.ssafy.commb.model.ConfirmationToken;
import com.ssafy.commb.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService{

    @Autowired
    private RedisService redisService;

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailSenderServiceImpl emailSenderService;

    // 이메일 인증 토큰 생성
    public String createEmailConfirmationToken(int userId, String receiverEmail) {

        Assert.hasText(String.valueOf(userId), "userId는 필수 입니다.");
        Assert.hasText(receiverEmail, "receiver Email은 필수 입니다.");

        ConfirmationToken emailConfirmationToken = ConfirmationToken.createEmailConfirmationToken(userId);
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByUserId(userId);
        confirmationToken.ifPresent(confirmationTokenRepository::delete);
        confirmationTokenRepository.save(emailConfirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);

        String query = "/checkEmailComplete" + "?key=" + emailConfirmationToken.getId();
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("링크를 클릭하시면 로그인 페이지로 이동합니다!\n"+"http://localhost:8080/users" + query);
        emailSenderService.sendEmail(mailMessage);          // 메일 발송

        return emailConfirmationToken.getId();
    }

    // 유효한 토큰 가져오기
    public Optional<ConfirmationToken> findByIdAndExpirationDateAfterAndExpired(String confirmationTokenId) {
        return confirmationTokenRepository.findByIdAndExpirationDateAfterAndExpired(confirmationTokenId, LocalDateTime.now(), false);
    };

}
