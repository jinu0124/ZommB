package com.ssafy.commb.repository;

import com.ssafy.commb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserById(int id);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findById(int id);

    Optional<User> findByIdAndPassword(int id, String password);

    Optional<List<User>> findByNicknameStartsWith(String nickname);


}
