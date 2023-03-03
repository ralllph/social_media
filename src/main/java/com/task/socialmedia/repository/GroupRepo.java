package com.task.socialmedia.repository;

import com.task.socialmedia.entity.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepo extends CrudRepository<Group, Long> {
    Optional<Group> findByGroupName(String groupName);

}
