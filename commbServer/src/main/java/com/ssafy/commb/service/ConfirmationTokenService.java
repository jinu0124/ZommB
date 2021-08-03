package com.ssafy.commb.service;

import com.ssafy.commb.model.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
    public String createEmailConfirmationToken(int userId, String receiverEmail);

    public Optional<ConfirmationToken> findByIdAndExpirationDateAfterAndExpired(String confirmationTokenId);
}
