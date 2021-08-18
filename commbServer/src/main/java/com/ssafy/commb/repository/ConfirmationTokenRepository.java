package com.ssafy.commb.repository;

import com.ssafy.commb.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {
    Optional<ConfirmationToken> findByIdAndExpirationDateAfterAndExpired(String confirmationTokenId, LocalDateTime now, boolean expired);

    Optional<ConfirmationToken> findByUserId(int userId);

    Optional<List<ConfirmationToken>> findByExpirationDateBefore(LocalDateTime now);
}
