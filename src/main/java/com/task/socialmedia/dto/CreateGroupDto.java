package com.task.socialmedia.dto;

import com.task.socialmedia.entity.User;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreateGroupDto {

    private Long id;

    private String groupName;

    private String groupDescription;

    private String groupPic;

    private Set<User> members = new HashSet<>();
}
