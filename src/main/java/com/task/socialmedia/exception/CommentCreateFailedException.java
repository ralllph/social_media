package com.task.socialmedia.exception;

public class CommentCreateFailedException  extends RuntimeException{
    public CommentCreateFailedException(Long userId, Long postId){
        super("Comment creation failed, either post  with  Id" + " "+ postId  + " " +  "or user" + " " +userId+ " "+"does not exist");
    }
}
