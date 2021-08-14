package com.ssafy.commb.service;

import com.ssafy.commb.exception.ApplicationException;
import com.ssafy.commb.model.ConfirmationToken;
import com.ssafy.commb.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService{

    @Value("${dynamic.path}")
    private String dynamicPath;

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailSenderServiceImpl emailSenderService;

    // 이메일 인증 토큰 생성
    public String createEmailConfirmationToken(int userId, String receiverEmail, String url) {
        Assert.hasText(String.valueOf(userId), "userId는 필수 입니다.");
        Assert.hasText(receiverEmail, "receiver Email은 필수 입니다.");

        ConfirmationToken emailConfirmationToken = ConfirmationToken.createEmailConfirmationToken(userId);
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByUserId(userId);
        confirmationToken.ifPresent(confirmationTokenRepository::delete);
        confirmationTokenRepository.save(emailConfirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        String query = "/checkEmailComplete" + "?key=" + emailConfirmationToken.getId() + "&url=" + url;
        mailMessage.setSubject("CommB 이메일 인증");
        mailMessage.setText("메일 인증을 위해 URL 링크를 통해 접속해주세요. \n"+ dynamicPath + "api/users" + query);

        emailSenderService.sendEmail(mailMessage);                                                                           // 메일 발송

        return emailConfirmationToken.getId();
    }

    // 유효한 토큰 가져오기
    public Optional<ConfirmationToken> findByIdAndExpirationDateAfterAndExpired(String confirmationTokenId) {
        return confirmationTokenRepository.findByIdAndExpirationDateAfterAndExpired(confirmationTokenId, LocalDateTime.now(ZoneId.of("+9")), false);
    }

    @Override
    public int findById(String key) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findById(key);
        if(confirmationToken.isPresent()) {
            int token = confirmationToken.get().getUserId();
            confirmationTokenRepository.delete(confirmationToken.get());
            return token;
        }

        throw new ApplicationException(HttpStatus.valueOf(401), "유효한 회원 인증 토큰을 찾을 수 없습니다. 서버에 문의");
    }

    @Override
    public void deleteLast() {
        Optional<List<ConfirmationToken>> confirmationTokens = confirmationTokenRepository.findByExpirationDateBefore(LocalDateTime.now(ZoneId.of("+9")));

        confirmationTokens.ifPresent(select -> {
            confirmationTokenRepository.deleteAll(confirmationTokens.get());
        });
    }


}
