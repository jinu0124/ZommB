package com.ssafy.commb.service;

public interface CommentService {

    public void uploadComment(int feedId, int userId, String content);

    public void modifyComment(int commentId, String content, int feedId);

    public int getUserId(int commentId);

    public void deleteComment(int commentId, int feedId, int userId);

    public void likeComment(int feedId, int commentId, int userId);

    public void deleteLikeComment(int feedId, int commentId, int userId);

}
