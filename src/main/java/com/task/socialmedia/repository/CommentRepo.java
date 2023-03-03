package com.task.socialmedia.repository;

import com.task.socialmedia.entity.Comments;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo  extends CrudRepository<Comments, Long> {
}
