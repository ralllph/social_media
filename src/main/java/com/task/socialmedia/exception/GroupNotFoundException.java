package com.task.socialmedia.exception;

public class GroupNotFoundException extends RuntimeException{
    public GroupNotFoundException(Long id){
        super("The group with id" + ""+ id +  " does not exist in our record ");
    }
}
