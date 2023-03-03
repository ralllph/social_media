package com.task.socialmedia.converter;

import com.task.socialmedia.dto.CommentDto;
import com.task.socialmedia.dto.ViewCommentDto;
import com.task.socialmedia.entity.Comments;

public interface CommentConv {
    Comments commentDtoToEntity(CommentDto commentDto);
    ViewCommentDto commentEntityToDto(Comments comment);
}
