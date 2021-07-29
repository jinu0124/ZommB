package com.ssafy.commb.service;

import com.ssafy.commb.model.ConfirmationToken;
import com.ssafy.commb.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailSenderService emailSenderService;

    // 이메일 인증 토큰 생성
    public String createEmailConfirmationToken(int userId, String receiverEmail) {

        Assert.hasText(String.valueOf(userId), "userId는 필수 입니다.");
        Assert.hasText(receiverEmail, "receiverEmail은 필수 입니다.");

        ConfirmationToken emailConfirmationToken = ConfirmationToken.createEmailConfirmationToken(userId);
        confirmationTokenRepository.save(emailConfirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("링크를 클릭하시면 로그인 페이지로 이동합니다!\n"+"http://localhost:8080/user/login");
        emailSenderService.sendEmail(mailMessage);

        return emailConfirmationToken.getId();
    }

    // 유효한 토큰 가져오기
    public ConfirmationToken findByIdAndExpirationDateAfterAndExpired(String confirmationTokenId) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByIdAndExpirationDateAfterAndExpired(confirmationTokenId, LocalDateTime.now(), false);
        return confirmationToken.orElseThrow(() -> new IllegalStateException("TOKEN_NOT_FOUND"));
    };

}
