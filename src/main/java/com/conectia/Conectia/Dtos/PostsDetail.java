package com.conectia.Conectia.Dtos;


import com.conectia.Conectia.Models.Comment;
import com.conectia.Conectia.Models.Post;
import jakarta.persistence.JoinColumn;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostsDetail(
        UUID id,
        String content,
        int likes,
        LocalDateTime date,
        String post_useremail,
        List<Comment> comments

) {
    public PostsDetail(Post P) {
        this(P.getId(), P.getContent(), P.getLikes(), P.getDate(), P.getPost_useremail(), P.getComments()
        );
    }
}
