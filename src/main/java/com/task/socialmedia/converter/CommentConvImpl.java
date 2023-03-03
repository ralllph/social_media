package com.task.socialmedia.converter;

import com.task.socialmedia.dto.CommentDto;
import com.task.socialmedia.dto.ViewCommentDto;
import com.task.socialmedia.entity.Comments;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CommentConvImpl  implements  CommentConv{

    private ModelMapper modelMapper;

    @Override
    public Comments commentDtoToEntity(CommentDto commentDto) {

        return modelMapper.map(commentDto, Comments.class);
    }

    @Override
    public ViewCommentDto commentEntityToDto(Comments comment) {
        ViewCommentDto viewComment = new ViewCommentDto();
        viewComment.setPostId(comment.getPost().getId());
        viewComment.setUserId(comment.getUser().getId());
        viewComment.setId(comment.getId());
        viewComment.setComment(comment.getComment());
        return viewComment;
    }
}
