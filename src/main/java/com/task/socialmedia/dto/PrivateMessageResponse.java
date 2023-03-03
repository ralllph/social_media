package com.task.socialmedia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//class used to format message sent to the reciever
public class PrivateMessageResponse {
    private String senderUsername;
    private String content;
}
