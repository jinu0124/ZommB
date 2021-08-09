package com.ssafy.commb.repository;

import com.ssafy.commb.model.Comment;
import com.ssafy.commb.model.CommentThumb;
import com.ssafy.commb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentThumbRepository extends JpaRepository<CommentThumb, Integer> {

    Optional<CommentThumb> findByCommentAndUser(Comment comment, User user);
}
