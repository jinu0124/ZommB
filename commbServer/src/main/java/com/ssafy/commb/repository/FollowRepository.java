package com.ssafy.commb.repository;

import com.ssafy.commb.model.User;
import com.ssafy.commb.model.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {

    public Optional<Follow> findByFollowerAndFollowing(User follower, User following);
}
