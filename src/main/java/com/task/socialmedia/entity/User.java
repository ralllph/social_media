package com.task.socialmedia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Table(name = "users")
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name ="user_name",nullable = false, unique = true)
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "profile_pic")
    private String profilePic;

    @Column(name = "cover_pic")
    private String coverPic;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Comments> comments;

    @ManyToMany
    @JoinTable(name = "user_friend",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    @JsonIgnore
    private Set<User> friends = new HashSet<>();

    //mapped by to prevent an extra table being created aside the join table in groups
    @ManyToMany(mappedBy = "members")
    private Set<Group> groups;

    @Override
    //generated function equals to help with the comparing of objects using contains in set
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId().equals(user.getId()) && getUserName().equals(user.getUserName()) && getFirstName().equals(user.getFirstName()) && getLastName().equals(user.getLastName()) && Objects.equals(getProfilePic(), user.getProfilePic()) && Objects.equals(getCoverPic(), user.getCoverPic()) && getPassword().equals(user.getPassword());
    }

    @Override
    //generated function hash to help with the comparing of objects using contains in set
    public int hashCode() {
        return Objects.hash(getId(), getUserName(), getFirstName(), getLastName(), getProfilePic(), getCoverPic(), getPassword());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", coverPic='" + coverPic + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
