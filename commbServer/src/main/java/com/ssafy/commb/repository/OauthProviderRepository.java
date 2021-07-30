package com.ssafy.commb.repository;

import com.ssafy.commb.model.OauthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthProviderRepository extends JpaRepository<OauthProvider, Integer> {
}
