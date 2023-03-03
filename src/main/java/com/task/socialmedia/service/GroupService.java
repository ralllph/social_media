package com.task.socialmedia.service;


import com.task.socialmedia.dto.CreateGroupDto;
import com.task.socialmedia.dto.UserCreatedDto;
import com.task.socialmedia.dto.ViewGroupDto;
import com.task.socialmedia.dto.ViewPostDto;

import java.util.List;
import java.util.Set;


public interface GroupService {

    ViewGroupDto createGroup(CreateGroupDto createGroupDto);

    UserCreatedDto addUserToGroup(Long userId,  Long groupId);

    Set<UserCreatedDto> viewGroupMembers(Long groupId);;

    void removeUserFromGroup(Long userId, Long groupId);

    ViewGroupDto findByGroupName(String groupName);

    List<ViewPostDto> viewGroupPosts(Long groupId);
}
