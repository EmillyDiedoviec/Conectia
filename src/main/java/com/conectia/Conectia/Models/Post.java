package com.conectia.Conectia.Models;

import com.conectia.Conectia.Dtos.AddPost;
import com.conectia.Conectia.Dtos.EditPost;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String content;
    private int likes;

    @CreationTimestamp
    private LocalDateTime date;

    private String post_useremail;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_post")
    private List<Comment> comments;

    public Post(AddPost newPost, String userEmail) {
        content = newPost.content();
        post_useremail = newPost.userEmail();
        comments = new ArrayList<Comment>();
    }

    public void EditPost(EditPost postEdited) {
        if(postEdited.content() != null) {
            content = postEdited.content();
        }
    }

}
