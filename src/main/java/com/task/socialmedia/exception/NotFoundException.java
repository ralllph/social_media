package com.task.socialmedia.exception;

public class NotFoundException  extends RuntimeException{
    public NotFoundException(){
        super( "entries do not exist in our record");
    }
}
