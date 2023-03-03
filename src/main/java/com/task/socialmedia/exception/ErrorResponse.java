package com.task.socialmedia.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//this class helps create an object for our error response
public class ErrorResponse {
    //the error message should be a list  cuz they might default in filling so amny fields like serial,moddel...
    private List<String> message;
    private String timestamp;

    //giving our timestamp a nice pattern
    DateTimeFormatter timeStampPattern  = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

    public ErrorResponse(List<String> message) {
        this.message = message;
        this.timestamp = timeStampPattern.format(LocalDateTime.now());
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
