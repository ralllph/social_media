package com.task.socialmedia.converter;

import com.task.socialmedia.dto.CreateGroupDto;
import com.task.socialmedia.dto.ViewGroupDto;
import com.task.socialmedia.entity.Group;




public interface GroupConv {

    Group groupDtoToEntity(CreateGroupDto groupDto);

    ViewGroupDto groupEntityToDto(Group group);
}
