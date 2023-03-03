package com.task.socialmedia.dto;

import lombok.Data;

@Data
public class ViewCommentDto {

    private Long id;

    private String comment;

    private Long userId;

    private Long postId;

}
