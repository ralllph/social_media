package com.task.socialmedia.exception;

public class PostCreateFailedException extends RuntimeException{
    public PostCreateFailedException(Long id){
        super("The user with  id" + " "+ id + " " +  "to make this post could not be found");
    }
}
