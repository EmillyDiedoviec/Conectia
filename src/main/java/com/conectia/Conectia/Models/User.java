package com.conectia.Conectia.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails{
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
