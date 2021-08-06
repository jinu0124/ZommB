package com.ssafy.commb.service;

import com.ssafy.commb.dto.user.MyDto;
import com.ssafy.commb.exception.follow.DuplicateFollowException;
import com.ssafy.commb.exception.follow.NotFoundFollowException;
import com.ssafy.commb.exception.user.NotFoundUserException;
import com.ssafy.commb.model.User;
import com.ssafy.commb.model.follow.Follow;
import com.ssafy.commb.model.follow.Followers;
import com.ssafy.commb.model.follow.Followings;
import com.ssafy.commb.repository.FollowRepository;
import com.ssafy.commb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        // 존재하지 않는 사용자가 있따면 exception
        if(!followerUser.isPresent() || !followingUser.isPresent()) throw new NotFoundUserException();

        // 이미 존재하는 팔로우일 경우 exception
        if(isFollow(followerUser.get(), followingUser.get())) throw new DuplicateFollowException();

        // 팔로우 추가
        followerUser.get().follow(followingUser.get());
    }

    /**
     *
     * @param follower 팔로우 취소하는 유저
     * @param following 언팔로우 대상
     */
    @Override
    public void deleteFollowing(int follower, int following) {
        Optional<User> followerUser = userRepository.findUserById(follower);
        Optional<User> followingUser = userRepository.findUserById(following);

        // 존재하지 않는 사용자가 있따면 exception
        if(!followerUser.isPresent() || !followingUser.isPresent()) throw new NotFoundUserException();

        // 존재하지 않는 팔로우일 경우 exception
        if(isFollow(followerUser.get(), followingUser.get())) throw new NotFoundFollowException();

        // 안팔로우
        followerUser.get().unfollow(followingUser.get());
    }

    /**
     *
     * @param meId : 현재 로그인한 사용자
     * @param userId : 팔로잉 목록의 대상
     * @return : MyDtoList
     */
    public List<MyDto> getFollowings(int meId, int userId){

        Optional<User> userOp = userRepository.findUserById(userId);
        Optional<User> meOp = userRepository.findUserById(meId);

        if(!userOp.isPresent() || !meOp.isPresent()) throw new NotFoundUserException();

        User user = userOp.get();
        User me = meOp.get();

        // 타겟유저의 following 목록 가져오기
        Followings followings = user.getFollowings();

        // 나(me)를 기준으로 follow 여부 확인하며, MyDtoList 생성
        List<MyDto> myDtoList = followings.getFollowings()
                .stream()
                .map(follow -> {
                    User following = userRepository.findUserById(follow.getFollowing().getId()).get();

                    return MyDto.builder()
                            .id(following.getId())
                            .nickname(following.getNickname())
                            .userFileUrl(following.getFileUrl())
                            .isFollow(isFollow(me, following))
                            .build();
                }).collect(Collectors.toList());

        return myDtoList;
    }

    /**
     *
     * @param meId : 현재 로그인한 사용자
     * @param userId : 팔로워 목록의 대상
     * @return : MyDtoList
     */
    @Override
    public List<MyDto> getFollowers(int meId, int userId) {
        Optional<User> userOp = userRepository.findUserById(userId);
        Optional<User> meOp = userRepository.findUserById(meId);

        if(!userOp.isPresent() || !meOp.isPresent()) {
            // User NotFound Exception 추가
            return null;
        }

        User user = userOp.get();
        User me = meOp.get();

        // 타겟유저의 following 목록 가져오기
        Followers followers = user.getFollowers();

        // 나(me)를 기준으로 follow 여부 확인하며, MyDtoList 생성
        List<MyDto> myDtoList = followers.getFollowers()
                .stream()
                .map(follow -> {
                    User follower = userRepository.findUserById(follow.getFollower().getId()).get();

                    return MyDto.builder()
                            .id(follower.getId())
                            .nickname(follower.getNickname())
                            .userFileUrl(follower.getFileUrl())
                            .isFollow(isFollow(me, follower))
                            .build();
                }).collect(Collectors.toList());

        return myDtoList;
    }


    /**
     *
     * @param from : 팔로우 기준
     * @param to : 팔로우 상대
     * @return : 팔로우 여부
     */
    public Boolean isFollow(User from, User to){
        Optional<Follow> follow = followRepository.findByFollowerAndFollowing(from, to);

        if(follow.isPresent()) return true;

        return false;
    }
}
