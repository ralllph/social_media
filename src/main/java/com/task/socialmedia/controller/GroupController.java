package com.task.socialmedia.controller;

import com.task.socialmedia.dto.CreateGroupDto;
import com.task.socialmedia.dto.UserCreatedDto;
import com.task.socialmedia.dto.ViewGroupDto;
import com.task.socialmedia.dto.ViewPostDto;
import com.task.socialmedia.service.GroupService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
public class GroupController {

    private GroupService groupService;

    //create group
    @PostMapping("/createGroup")
    public ResponseEntity<ViewGroupDto> createGroup(@RequestBody @Valid  CreateGroupDto createGroupDto){
        return new ResponseEntity<>(groupService.createGroup(createGroupDto), HttpStatus.CREATED);
    }

    //Add user to group
    @PutMapping("/user/{userId}/group/{groupId}")
    public ResponseEntity<UserCreatedDto> joinGroup(@PathVariable Long userId, @PathVariable Long groupId){
        return new ResponseEntity<>(groupService.addUserToGroup(userId,groupId), HttpStatus.OK);
    }

    //Get group members
    @GetMapping("/getMembers/group/{groupId}")
    public ResponseEntity<Set<UserCreatedDto>> getGroupMembers(@PathVariable Long groupId){
        return new ResponseEntity<>(groupService.viewGroupMembers(groupId), HttpStatus.OK);
    }

    //remove user from group
    @PutMapping("/exitGroup/user/{userId}/group/{groupId}")
    public ResponseEntity<HttpStatus> removeUserFromGroup(@PathVariable Long userId, @PathVariable Long groupId){
        groupService.removeUserFromGroup(userId,groupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //find group by name
    @GetMapping("/findGroup/{groupName}")
    public ResponseEntity<ViewGroupDto> findGroupByName(@PathVariable String groupName){
        return new ResponseEntity<>(groupService.findByGroupName(groupName), HttpStatus.OK);
    }

    //view Group Posts
    @GetMapping("/viewGroupPosts/{groupId}")
    public ResponseEntity<List<ViewPostDto >> viewGroupPosts(@PathVariable Long groupId){
        return new ResponseEntity<>(groupService.viewGroupPosts(groupId), HttpStatus.OK);
    }
}
