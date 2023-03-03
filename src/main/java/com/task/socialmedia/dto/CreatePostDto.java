package com.task.socialmedia.dto;

import com.task.socialmedia.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreatePostDto {

    private Long id;

    @NotBlank(message = "post must contain at least text")
    private String text;

    private String image;

    private String videos;

    private User user;
}
