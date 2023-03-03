package com.task.socialmedia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.task.socialmedia.entity.Comments;
import com.task.socialmedia.entity.Post;
import com.task.socialmedia.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {

    private Long id;

    @NotBlank(message = "first name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    private String profilePic;

    private String coverPic;

    @NotBlank(message = "user name cannot be blank")
    private String userName;

    @NotBlank(message = "password cannot be blank")
    private String password;


}
