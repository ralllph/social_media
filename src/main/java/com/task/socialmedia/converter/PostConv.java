package com.task.socialmedia.converter;

import com.task.socialmedia.dto.CreatePostDto;
import com.task.socialmedia.dto.ViewPostDto;
import com.task.socialmedia.entity.Post;

public interface PostConv {
    CreatePostDto postEntityToDto(Post post);
    Post postDtoToEntity( CreatePostDto postDto);
    ViewPostDto postEntityToViewPostDto( Post post);

    Post viewPostDtoToEntity(ViewPostDto viewPostDto);
}
