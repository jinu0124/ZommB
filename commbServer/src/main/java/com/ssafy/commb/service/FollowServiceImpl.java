package com.ssafy.commb.service;

import com.ssafy.commb.model.User;
import com.ssafy.commb.repository.FollowRepository;
import com.ssafy.commb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class FollowServiceImpl implements FollowService{

    @Autowired
    FollowRepository followRepository;

    @Autowired
    UserRepository userRepository;


    /**
     *
     * @param follower : 팔로우하는 사람
     * @param following :팔로우 대상
     * @return : 생성된 팔로우 정보
     */
    @Override
    public void addFollowing(int follower, int following) {

        Optional<User> followerUser = userRepository.findUserById(follower);
        Optional<User> followingUser = userRepository.findUserById(following);

        if(followerUser.isPresent() && followingUser.isPresent())
            followerUser.get().follow(followingUser.get());

//        return followRepository.save(follow);
    }

    @Override
    public void deleteFollowing(int follower, int following) {
        Optional<User> followerUser = userRepository.findUserById(follower);
        Optional<User> followingUser = userRepository.findUserById(following);


        System.out.println(followerUser.get().getFollowings().count());
        System.out.println(followingUser.get().getFollowers().count());

        if(followerUser.isPresent() & followingUser.isPresent())
            followerUser.get().unfollow(followingUser.get());
    }
}
