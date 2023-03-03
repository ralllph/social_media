package com.task.socialmedia.exception;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(Long id){
        super("The comment with  id" + " "+ id + " " +  "does not exist in our record");
    }
}
