package com.task.socialmedia.dto;

import com.task.socialmedia.entity.Post;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentDto {

    private Long id;

    @NotBlank(message = "comment can't be blank")
    private String comment;


    private Post post;
}
