package com.task.socialmedia.service;

import com.task.socialmedia.converter.GroupConv;
import com.task.socialmedia.converter.PostConv;
import com.task.socialmedia.converter.UserConv;
import com.task.socialmedia.dto.CreateGroupDto;
import com.task.socialmedia.dto.UserCreatedDto;
import com.task.socialmedia.dto.ViewGroupDto;
import com.task.socialmedia.dto.ViewPostDto;
import com.task.socialmedia.entity.Group;
import com.task.socialmedia.entity.Post;
import com.task.socialmedia.entity.User;
import com.task.socialmedia.exception.GroupNotFoundException;
import com.task.socialmedia.exception.NotFoundException;
import com.task.socialmedia.repository.GroupRepo;
import com.task.socialmedia.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class GroupServiceImpl  implements  GroupService{

    private GroupConv groupConv;

    private GroupRepo groupRepo;

    private UserRepo userRepo;

    private UserConv userConv;

    private PostConv postConv;

    @Override
    public ViewGroupDto createGroup(CreateGroupDto createGroupDto) {
        Group group = groupConv.groupDtoToEntity(createGroupDto);
        Group savedGroup = groupRepo.save(group);
        return groupConv.groupEntityToDto(savedGroup);
    }

    @Override
    public UserCreatedDto addUserToGroup(Long userId, Long groupId) {
        Optional<User> userFound = userRepo.findById(userId);
        Optional<Group> groupFound = groupRepo.findById(groupId);
        if(userFound.isPresent() && groupFound.isPresent()){
            //add bidirectionally
            userFound.get().getGroups().add(groupFound.get());
            groupFound.get().getMembers().add(userFound.get());
            userRepo.save(userFound.get());
            groupRepo.save(groupFound.get());
        }else{
            throw new  NotFoundException();
        }
        return userConv.userEntityToUserCreatedDto(userFound.get());
    }

    @Override
    public Set<UserCreatedDto> viewGroupMembers(Long groupId) {
        Optional<Group> foundGroup = groupRepo.findById(groupId);
        Set<UserCreatedDto> membersListDto = new HashSet<>();
        if(foundGroup.isPresent()){
            Set<User> members = foundGroup.get().getMembers();
            for(User member:members){
                UserCreatedDto memberInGroup = userConv.userEntityToUserCreatedDto(member);
                membersListDto.add(memberInGroup);
            }

        }else{
            throw new GroupNotFoundException(groupId);
        }
        return membersListDto;
    }

    @Override
    public void removeUserFromGroup(Long userId, Long groupId) {
        Optional<User> userFound = userRepo.findById(userId);
        Optional<Group> groupFound = groupRepo.findById(groupId);
        if(userFound.isPresent() && groupFound.isPresent()){
            User userNowFound = userFound.get();
            Group groupNowFound = groupFound.get();
            //add bidirectionally
            userNowFound.getGroups().remove(groupNowFound);
            groupNowFound.getMembers().remove(userNowFound);
            userRepo.save(userNowFound);
            groupRepo.save(groupNowFound);
        }else{
            throw new  NotFoundException();
        }
    }

    @Override
    public ViewGroupDto findByGroupName(String groupName) {
        Optional<Group> foundGroup = groupRepo.findByGroupName(groupName);
        if(foundGroup.isPresent()){
            Group groupFound = foundGroup.get();
            return groupConv.groupEntityToDto(groupFound);
        }else{
            throw new NotFoundException();
        }
    }

    @Override
    public List<ViewPostDto> viewGroupPosts(Long groupId) {
        Optional<Group> groupFound = groupRepo.findById(groupId);
        if(groupFound.isPresent()){
            List<ViewPostDto>  postList = new ArrayList<>();
            Group groupNowFound = groupFound.get();
            List<Post> groupPosts = groupNowFound.getPosts();
            if(postList.size()!=0) {
                for (Post post : groupPosts) {
                    ViewPostDto postElement = postConv.postEntityToViewPostDto(post);
                    postList.add(postElement);
                }
            }
            return postList;
        }else{
            throw new GroupNotFoundException(groupId);
        }
    }
}
