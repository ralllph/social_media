package com.task.socialmedia.controller;

import com.task.socialmedia.dto.CommentDto;
import com.task.socialmedia.dto.ViewCommentDto;
import com.task.socialmedia.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @PostMapping("/comment/user/{userId}/post/{postId}")
    public ResponseEntity<ViewCommentDto> createComment(@RequestBody @Valid CommentDto commentDto, @PathVariable Long postId , @PathVariable Long userId) {
        return new ResponseEntity<>(commentService.createComment(commentDto,userId, postId), HttpStatus.CREATED);
    }

    @GetMapping("/getComment/{commentId}")
    public ResponseEntity<ViewCommentDto> getComment(@PathVariable Long commentId){
        return new ResponseEntity<>(commentService.viewComment(commentId), HttpStatus.OK);
    }
}
