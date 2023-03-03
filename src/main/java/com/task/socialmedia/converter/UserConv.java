package com.task.socialmedia.converter;

import com.task.socialmedia.dto.UserDto;
import com.task.socialmedia.dto.UserCreatedDto;
import com.task.socialmedia.entity.User;

import java.util.List;

public interface UserConv {
    UserDto userEntityToDto(User user);
    User userDtoToEntity(UserDto userDto);
    UserCreatedDto userEntityToUserCreatedDto(User user);
    List<UserDto> userListToDto(List<User> userList);
}
