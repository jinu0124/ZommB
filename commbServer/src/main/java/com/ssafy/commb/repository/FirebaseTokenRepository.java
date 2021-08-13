package com.ssafy.commb.repository;

import com.ssafy.commb.model.FirebaseToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FirebaseTokenRepository extends JpaRepository<FirebaseToken, Integer> {
    public Optional<FirebaseToken> findByUserIdAndToken(int userId, String token);

    public Optional<List<FirebaseToken>> findByUserId(int userId);

    public Optional<List<FirebaseToken>> findByUserIdIn(List<Integer> userId);

    public Optional<FirebaseToken> findByToken(String token);

}
