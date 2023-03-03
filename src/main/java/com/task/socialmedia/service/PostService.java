package com.task.socialmedia.service;

import com.task.socialmedia.dto.CreatePostDto;
import com.task.socialmedia.dto.ViewPostDto;

import java.util.List;

public interface PostService {
    ViewPostDto createPost(CreatePostDto post, Long userId);
    ViewPostDto viewPost(Long postId);

    List<ViewPostDto> viewFriendsPost(Long userId);

}
