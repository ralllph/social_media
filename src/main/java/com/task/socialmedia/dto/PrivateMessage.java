package com.task.socialmedia.dto;

import lombok.Data;

@Data
public class PrivateMessage {
    private String receiverUsername;
    private String content;
}
