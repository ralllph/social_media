package com.task.socialmedia.controller;

import com.task.socialmedia.dto.CreatePostDto;
import com.task.socialmedia.dto.ViewPostDto;
import com.task.socialmedia.service.PostService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class PostController {

    private PostService postService;

    @PostMapping("/createPost/{userId}")
    public ResponseEntity<ViewPostDto > createPost(@RequestBody @Valid CreatePostDto postDto , @PathVariable Long userId ){
        return new ResponseEntity<>(postService.createPost(postDto,userId), HttpStatus.CREATED);
    }

    @GetMapping("/viewPost/{postId}")
    public ResponseEntity<ViewPostDto> viewPost(@PathVariable Long postId){
        return new ResponseEntity<>(postService.viewPost(postId), HttpStatus.OK);
    }

    //get friend posts
    @GetMapping("/getFriendsPosts/{userId}")
    public ResponseEntity<List<ViewPostDto>> viewFriendsPosts(@PathVariable Long userId){
        return new ResponseEntity<>(postService.viewFriendsPost(userId), HttpStatus.OK);
    }
}
