package com.task.socialmedia.service;

import com.task.socialmedia.dto.CommentDto;
import com.task.socialmedia.dto.ViewCommentDto;

public interface CommentService {
    ViewCommentDto createComment(CommentDto commentDto,Long userId, Long postId);

    ViewCommentDto viewComment(Long commentId);
}
