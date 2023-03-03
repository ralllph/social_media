package com.task.socialmedia.converter;

import com.task.socialmedia.dto.CreateGroupDto;
import com.task.socialmedia.dto.ViewGroupDto;
import com.task.socialmedia.entity.Group;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GroupConvImpl implements GroupConv{

    private ModelMapper modelMapper;
    @Override
    public Group groupDtoToEntity(CreateGroupDto groupDto) {
        return modelMapper.map(groupDto, Group.class);
    }

    @Override
    public ViewGroupDto groupEntityToDto(Group group) {
        return modelMapper.map(group, ViewGroupDto.class);
    }
}
