package com.task.socialmedia.exception;

public class NoFriendsException extends RuntimeException{
    public NoFriendsException(Long userId){
        super( "The user with id" + userId + " " + " " + "has no friends");
    }
}
