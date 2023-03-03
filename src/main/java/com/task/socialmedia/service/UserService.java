package com.task.socialmedia.service;

import com.task.socialmedia.dto.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface UserService {
    UserCreatedDto createUser(UserDto user);
    UserDto findUserById(Long id);
    UserDto findByUsername(String userName);
    UserCreatedDto updateUser(Long userId, UserDto user);
    List<UserDto> getAllUsers();
    UserCreatedDto  addFriend(Long userId,  Long friendId);
    void removeFriend(Long userId, Long friendId);
   Set<UserCreatedDto> getFriends(Long userId);
   ViewPostDto postOnGroup(Long userId, CreatePostDto createPostDto, Long groupId);

   void sendPrivateMessage(Principal principal, PrivateMessage message);
}
