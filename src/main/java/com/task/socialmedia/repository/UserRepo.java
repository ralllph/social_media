package com.task.socialmedia.repository;

import com.task.socialmedia.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long> {
    Optional<User> findByUserName(String userName);

}
