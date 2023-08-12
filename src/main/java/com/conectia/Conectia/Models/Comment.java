package com.conectia.Conectia.Models;

import com.conectia.Conectia.Dtos.AddComment;
import com.conectia.Conectia.Dtos.EditComment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_comment;
    private String comment;
    private int likes;
    @CreationTimestamp
    private LocalDateTime date;

    private String comment_useremail;

    private UUID id_post;

    public Comment(AddComment newComment, String userEmail, UUID idPost) {
        comment = newComment.comment();
        comment_useremail = newComment.userEmail();
        id_post = newComment.idPost();
    }

    public void EditComment(EditComment commentEdited) {
        if(commentEdited.comment() != null) {
            comment = commentEdited.comment();
        }
    }
}
