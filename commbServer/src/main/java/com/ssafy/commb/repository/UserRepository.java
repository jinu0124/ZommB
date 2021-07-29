package com.ssafy.commb.repository;

import com.ssafy.commb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserById(int userId);

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User findById(int id);


}
