package com.task.socialmedia.converter;

import com.task.socialmedia.dto.CreatePostDto;
import com.task.socialmedia.dto.ViewPostDto;
import com.task.socialmedia.entity.Post;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostConvImpl implements  PostConv{

    private ModelMapper modelMapper;

    @Override
    public CreatePostDto postEntityToDto(Post post) {
        return modelMapper.map(post, CreatePostDto.class);
    }

    @Override
    public Post postDtoToEntity(CreatePostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

    @Override
    public ViewPostDto postEntityToViewPostDto(Post post) {
        ViewPostDto viewPost = new ViewPostDto();
        viewPost.setText(post.getText());
        viewPost.setUser(post.getUser().getId());
        viewPost.setId(post.getId());
        viewPost.setImage(post.getImage());
        viewPost.setVideos(post.getVideos());
        return viewPost;
    }

    @Override
    public Post viewPostDtoToEntity(ViewPostDto viewPostDto) {
        return modelMapper.map(viewPostDto, Post.class);
    }
}
