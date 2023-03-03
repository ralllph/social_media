package com.task.socialmedia.repository;

import com.task.socialmedia.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post, Long> {
}
