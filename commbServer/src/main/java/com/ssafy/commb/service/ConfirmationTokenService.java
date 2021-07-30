package com.ssafy.commb.service;

import com.ssafy.commb.model.ConfirmationToken;

public interface ConfirmationTokenService {
    public String createEmailConfirmationToken(int userId, String receiverEmail);

    public ConfirmationToken findByIdAndExpirationDateAfterAndExpired(String confirmationTokenId);
}
