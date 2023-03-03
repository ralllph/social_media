package com.task.socialmedia.entity;

import lombok.Data;
import javax.persistence.*;
;
@Data
@Entity
@Table(name = "comments")
public class Comments {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "commented", referencedColumnName = "id")
    private User user;

}
