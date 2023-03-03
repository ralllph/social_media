package com.task.socialmedia.service;

import com.task.socialmedia.config.PasswordEncoder;
import com.task.socialmedia.converter.PostConv;
import com.task.socialmedia.converter.UserConv;
import com.task.socialmedia.dto.*;
import com.task.socialmedia.entity.Group;
import com.task.socialmedia.entity.Post;
import com.task.socialmedia.entity.User;
import com.task.socialmedia.exception.NotFoundException;
import com.task.socialmedia.exception.UserNotFoundException;
import com.task.socialmedia.repository.GroupRepo;
import com.task.socialmedia.repository.PostRepo;
import com.task.socialmedia.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl  implements  UserService{

    private UserRepo userRepo;
    private UserConv userConv;
    private PasswordEncoder passwordEncoder;

    private GroupRepo groupRepo;

    private ModelMapper modelMapper;

    private PostService postService;

    private PostConv  postConv;

    private PostRepo postRepo;

    //sending message
    private SimpMessageSendingOperations messageTemplate;
    @Override
    public UserCreatedDto createUser(UserDto user) {
        User userEntity = userConv.userDtoToEntity(user);
        String userPassword = userEntity.getPassword();
        String encryptedUserPass = passwordEncoder.bCryptPasswordEncoder().encode(userPassword);
        userEntity.setPassword(encryptedUserPass);
        User savedUser = userRepo.save(userEntity);
        return  userConv.userEntityToUserCreatedDto(savedUser);
    }

    @Override
    public UserDto findUserById(Long id) {
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            return userConv.userEntityToDto(user.get());
        }else{
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public UserDto findByUsername(String userName) {
        Optional<User> user = userRepo.findByUserName(userName);
        if(user.isPresent()){
            User foundUser = user.get();
            return userConv.userEntityToDto(foundUser);
        }
        else{
            throw  new NotFoundException();
        }
    }

    @Override
    public UserCreatedDto updateUser(Long userId, UserDto user) {
        Optional<User> userToUpdate = userRepo.findById(userId);
        if(userToUpdate.isPresent()) {
            User userToBeUpdated = userToUpdate.get();
             modelMapper.map(user, userToBeUpdated);
             userToBeUpdated.setId(userId);
             User savedUpdatedUser = userRepo.save(userToBeUpdated);
            return userConv.userEntityToUserCreatedDto(savedUpdatedUser);
        }else{
            throw new UserNotFoundException(userId);
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        Iterable<User> users = userRepo.findAll();
        List<User> userEntityList = convertIterableToList(users);
        if(userEntityList.size() > 0){
            return userConv.userListToDto(userEntityList);
        }else{
            throw new UserNotFoundException(404L);
        }

    }

    @Override
    public UserCreatedDto addFriend(Long userId, Long friendId) {
        Optional<User> user = userRepo.findById(userId);
        Optional<User> friend = userRepo.findById(friendId);
        if(user.isPresent() && friend.isPresent()){
            User userMakingFriend = user.get();
            User newFriend = friend.get();
            //add friend to the new user
            userMakingFriend.getFriends().add(newFriend);
            //save the user making friend
            userRepo.save(userMakingFriend);
            // bi-directional. the friend you added also made a friend to the user making friend
            newFriend.getFriends().add(userMakingFriend);
            userRepo.save(newFriend);
            return userConv.userEntityToUserCreatedDto(newFriend);
        }else{
            throw new NotFoundException();
        }
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        Optional<User> user  = userRepo.findById(userId);
        Optional<User> friend = userRepo.findById(friendId);
        if(user.isPresent() && friend.isPresent()){
            User userWithFriend = user.get();
            User friendToRemove = friend.get();
            if(userWithFriend.getFriends().contains(friendToRemove)){
                //Bi directional removal
                userWithFriend.getFriends().remove(friendToRemove);
                friendToRemove.getFriends().remove(userWithFriend);
                userRepo.save(userWithFriend);
                userRepo.save(friendToRemove);
            }
        }else{
            throw new NotFoundException();
        }
    }

    @Override
    public Set<UserCreatedDto> getFriends(Long userId) {
        Optional<User> user= userRepo.findById(userId);
        if(user.isPresent()){
            Set<User> friendList = user.get().getFriends();
            Set<UserCreatedDto> friendListDto = new HashSet<>();
            for(User friend: friendList){
                UserCreatedDto friendDto = userConv.userEntityToUserCreatedDto(friend);
                friendListDto.add(friendDto);
            }
            return friendListDto;
        }else{
            throw new UserNotFoundException(userId);
        }
    }

    @Override
    public ViewPostDto postOnGroup(Long userId, CreatePostDto createPostDto, Long groupId) {
        Optional<User> userFound = userRepo.findById(userId);
        Optional<Group> groupFound = groupRepo.findById(groupId);
        if(userFound.isPresent() && groupFound.isPresent()){
            //create post with user
            ViewPostDto postCreated = postService.createPost(createPostDto,userId);
            Group groupEntity = groupFound.get();
            User userEntity = userFound.get();
            Post postToGroup = postConv.viewPostDtoToEntity(postCreated);
            postToGroup.setUser(userEntity);
            //add post to group
            groupEntity.getPosts().add(postToGroup);
            postToGroup.setGroup(groupEntity);
            //save
            groupRepo.save(groupEntity);
            postRepo.save(postToGroup);
            return postConv.postEntityToViewPostDto(postToGroup);
        }else{
            throw new NotFoundException();
        }

    }

    @Override
    public void sendPrivateMessage(Principal principal, PrivateMessage message) {
        Optional<User> sender = userRepo.findByUserName(principal.getName());
        Optional<User> receiver = userRepo.findByUserName(message.getReceiverUsername());
        if(sender.isPresent() && receiver.isPresent()){
            User theReceiver = receiver.get();
            User theSender = sender.get();
            messageTemplate.convertAndSendToUser(theReceiver.getUserName(),"/queue/private",
                    new PrivateMessageResponse(theSender.getUserName(), message.getContent()));
        }else{
            throw new NotFoundException();
        }
    }

    public List<User> convertIterableToList(Iterable<User> allDrones){
        //List to be returned
        List<User> userList = new ArrayList<>();
        for(User userElement: allDrones){
            userList.add(userElement);
        }
        return userList ;
    }
}
