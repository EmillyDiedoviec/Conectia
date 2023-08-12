package com.conectia.Conectia.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    private String email;
    private String password;
    private String name;
    private String born;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_useremail")
    private List<Post> posts;

    private List<String> followers;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_useremail")
    private List<Comment> comments;

    public User(String email, String password, String name, String born) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.born = born;
        posts = new ArrayList<>();
        followers = new ArrayList<>();
        comments = new ArrayList<>();
    }

}
