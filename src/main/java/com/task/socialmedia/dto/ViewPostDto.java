package com.task.socialmedia.dto;

import lombok.Data;
@Data
public class ViewPostDto {
    private Long id;

    private String text;

    private String image;

    private String videos;

    private Long user;

    @Override
    public String toString() {
        return "ViewPostDto{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", image='" + image + '\'' +
                ", videos='" + videos + '\'' +
                ", user=" + user +
                '}';
    }
}
