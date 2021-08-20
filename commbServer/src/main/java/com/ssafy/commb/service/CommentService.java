package com.ssafy.commb.service;

import com.ssafy.commb.dto.fcm.FcmDto;

import java.util.List;
import java.util.Map;

public interface CommentService {

    public int uploadComment(int feedId, int userId, String content);

    public void modifyComment(int commentId, String content, int feedId);

    public int getUserId(int commentId);

    public void deleteComment(int commentId, int feedId, int userId);

    public void likeComment(int feedId, int commentId, int userId);

    public void deleteLikeComment(int feedId, int commentId, int userId);

    public List<FcmDto> getFeedWritersFirebaseToken(int feedId, int userId, String content ,int commentId);


}
