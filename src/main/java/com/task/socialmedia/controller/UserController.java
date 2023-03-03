package com.task.socialmedia.controller;

import com.task.socialmedia.dto.*;
import com.task.socialmedia.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserCreatedDto> createUser(@RequestBody @Valid UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserCreatedDto> updateUser(@RequestBody @Valid UserDto userDto, @PathVariable Long userId){
        return new ResponseEntity<>(userService.updateUser(userId, userDto), HttpStatus.OK);
    }

    @GetMapping("/findUser/{userName}")
    public ResponseEntity<UserDto> findUser(@PathVariable String userName){
        return new ResponseEntity<>(userService.findByUsername(userName), HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/addFriend/user/{userId}/friend/{friendId}")
    public ResponseEntity<UserCreatedDto> addFriend(@PathVariable Long userId, @PathVariable Long friendId){
        return new ResponseEntity<>(userService.addFriend(userId,friendId), HttpStatus.OK);
    }

    @DeleteMapping("/removeFriend/user/{userId}/friend/{friendId}")
    public ResponseEntity<HttpStatus> removeFriend(@PathVariable Long userId, @PathVariable Long friendId){
        userService.removeFriend(userId,friendId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getFriends/{userId}")
    public ResponseEntity<Set<UserCreatedDto>> getFriends(@PathVariable Long userId){
        return new ResponseEntity<>(userService.getFriends(userId), HttpStatus.OK);
    }

    //user posting to group
    @PostMapping("/postToGroup/user/{userId}/group/{groupId}")
    public ResponseEntity<ViewPostDto> userPostToGroup(@RequestBody CreatePostDto createPostDto, @PathVariable Long
                                                       userId, @PathVariable Long groupId){
        return new ResponseEntity<>(userService.postOnGroup(userId,createPostDto,groupId),HttpStatus.CREATED);
    }

    //send direct message
    @MessageMapping("/send/privateMessage")
    public void sendPrivateMessage(Principal principal, PrivateMessage message){
        userService.sendPrivateMessage(principal, message);
    }

}
