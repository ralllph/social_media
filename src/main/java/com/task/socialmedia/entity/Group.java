package com.task.socialmedia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "social_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String groupName;

    @Column(name = "description")
    private String groupDescription;

    @Column(name = "group_pic")
    private String groupPic;

    @ManyToMany
    //we made thisthe owning side for no reason, mapped by is in User
    @JoinTable(
            name = "group_user",
            joinColumns = @JoinColumn(name = "groups_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id")
    )
    @JsonIgnore
    private Set<User> members;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<Post> posts;

}
