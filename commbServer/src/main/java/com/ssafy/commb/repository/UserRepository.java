package com.ssafy.commb.repository;

import com.ssafy.commb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
