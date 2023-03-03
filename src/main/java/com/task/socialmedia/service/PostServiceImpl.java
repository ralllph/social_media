package com.task.socialmedia.service;

import com.task.socialmedia.converter.PostConv;
import com.task.socialmedia.converter.UserConv;
import com.task.socialmedia.dto.CreatePostDto;
import com.task.socialmedia.dto.UserCreatedDto;
import com.task.socialmedia.dto.ViewPostDto;
import com.task.socialmedia.entity.Post;
import com.task.socialmedia.entity.User;
import com.task.socialmedia.exception.NoFriendsException;
import com.task.socialmedia.exception.PostCreateFailedException;
import com.task.socialmedia.exception.PostNotFoundException;
import com.task.socialmedia.exception.UserNotFoundException;
import com.task.socialmedia.repository.PostRepo;
import com.task.socialmedia.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class PostServiceImpl implements  PostService{
    private UserRepo userRepo;
    private UserConv userConv;
    private PostConv postConv;
    private PostRepo postRepo;
    @Override
    public ViewPostDto createPost(CreatePostDto post, Long userId) {
        Optional<User> userThatPosted = userRepo.findById(userId);
        if(userThatPosted.isPresent()){
            Post postCreated = postConv.postDtoToEntity(post);
            User userPosting = userThatPosted.get();
            //add bidirectionally
            postCreated.setUser(userPosting);
            userPosting.getPosts().add(postCreated);
            //save
            userRepo.save(userPosting);
            postRepo.save(postCreated);
            return postConv.postEntityToViewPostDto(postCreated);
        }else{
            throw new PostCreateFailedException(userId);
        }
    }

    @Override
    public ViewPostDto viewPost(Long postId) {
        Optional<Post> postFound = postRepo.findById(postId);
        if(postFound.isPresent()){
            return postConv.postEntityToViewPostDto(postFound.get());
        }else{
            throw new PostNotFoundException(postId);
        }
    }

    @Override
    public List<ViewPostDto> viewFriendsPost(Long userId) {
        Optional<User> userFound = userRepo.findById(userId);
        if (userFound.isPresent()){
            User userNowFound = userFound.get();
            List<ViewPostDto> friendsPostList = new ArrayList<>();
            Set<User> friends = userNowFound.getFriends();
            //add friends post to list
            if(friends.size() !=0) {
                for (User friend : friends) {
                    List<Post> friendPosts = friend.getPosts();
                    //add post if not empty
                    if(friendPosts.size() != 0) {
                        //get post list from each friend
                        for (Post friendPost :friendPosts) {
                            ViewPostDto eachPost = postConv.postEntityToViewPostDto(friendPost);
                            //add post dto version to the list
                            friendsPostList.add(eachPost);
                        }
                    }
                }
                return friendsPostList;
            }else{
                throw new NoFriendsException(userId);
            }
        }else{
            throw new UserNotFoundException(userId);
        }
    }
}
