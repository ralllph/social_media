package com.task.socialmedia.service;

import com.task.socialmedia.converter.CommentConv;
import com.task.socialmedia.dto.CommentDto;
import com.task.socialmedia.dto.ViewCommentDto;
import com.task.socialmedia.entity.Comments;
import com.task.socialmedia.entity.Post;
import com.task.socialmedia.entity.User;
import com.task.socialmedia.exception.CommentCreateFailedException;
import com.task.socialmedia.exception.CommentNotFoundException;
import com.task.socialmedia.repository.CommentRepo;
import com.task.socialmedia.repository.PostRepo;
import com.task.socialmedia.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentServiceImpl  implements CommentService{

    private CommentRepo commentRepo;
    private CommentConv commentConv;

    private UserRepo userRepo;
    private PostRepo postRepo;

    @Override
    public ViewCommentDto createComment(CommentDto commentDto,Long userId,  Long postId) {
        Optional<Post> postWithComment = postRepo.findById(postId);
        Optional<User> userCommenting = userRepo.findById(userId);
        if(postWithComment.isPresent() && userCommenting.isPresent()){
            Comments commentEntity = commentConv.commentDtoToEntity(commentDto);
            commentEntity.setPost(postWithComment.get());
            commentEntity.setUser(userCommenting.get());
            Comments savedComment = commentRepo.save(commentEntity);
            return commentConv.commentEntityToDto(savedComment);
        }else{
            throw new CommentCreateFailedException(userId,postId);
        }

    }

    @Override
    public ViewCommentDto viewComment(Long commentId) {
        Optional<Comments> commentFound = commentRepo.findById(commentId);
        if(commentFound.isPresent()){
            return commentConv.commentEntityToDto(commentFound.get());
        }else{
            throw new CommentNotFoundException(commentId);
        }
    }
}
