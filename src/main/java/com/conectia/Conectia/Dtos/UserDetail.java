package com.conectia.Conectia.Dtos;


import com.conectia.Conectia.Models.Comment;
import com.conectia.Conectia.Models.Post;
import com.conectia.Conectia.Models.User;

import java.util.Date;
import java.util.List;

public record UserDetail(
        String email,
        String password,
        String name,
        String born,
        List<Post> posts,
        List<String> followers,
        List<Comment> comments
) {
    public UserDetail(User U) {
        this(
                U.getEmail(),
                U.getPassword(),
                U.getName(),
                U.getBorn(),
                U.getPosts(),
                U.getFollowers(),
                U.getComments()
        );
    }
}
