package com.task.socialmedia.converter;

import com.task.socialmedia.dto.UserDto;
import com.task.socialmedia.dto.UserCreatedDto;
import com.task.socialmedia.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class UserConvImpl implements  UserConv {
    private ModelMapper modelMapper;
    @Override
    public UserDto userEntityToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User userDtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    @Override
    public UserCreatedDto userEntityToUserCreatedDto(User user) {
        return modelMapper.map(user, UserCreatedDto.class);
    }

    @Override
    public List<UserDto> userListToDto(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user: userList){
            userDtoList.add(userEntityToDto(user));
        }
        return userDtoList;
    }

}
