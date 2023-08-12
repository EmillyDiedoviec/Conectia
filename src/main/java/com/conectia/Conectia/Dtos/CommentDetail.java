package com.conectia.Conectia.Dtos;

import com.conectia.Conectia.Models.Comment;
import com.conectia.Conectia.Models.Post;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentDetail(
        UUID id_comment,
        String comment,
        int likes,
        LocalDateTime date,
        String comment_useremail,
        UUID idPost


) {
    public CommentDetail(Comment C) {
        this(C.getId_comment(), C.getComment(), C.getLikes(), C.getDate(), C.getComment_useremail(), C.getId_post());
    }
}
