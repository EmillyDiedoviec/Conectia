package com.conectia.Conectia.Controllers;

import com.conectia.Conectia.Dtos.AddPost;
import com.conectia.Conectia.Dtos.EditPost;
import com.conectia.Conectia.Dtos.ErrorData;
import com.conectia.Conectia.Dtos.PostsDetail;
import com.conectia.Conectia.Models.Post;
import com.conectia.Conectia.Repositories.PostRepository;
import com.conectia.Conectia.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/{email}")
    @Transactional
    public ResponseEntity addPost(@PathVariable String email, @RequestBody @Valid AddPost newPost){
        var user = userRepository.getByEmail(newPost.userEmail());

        if(user == null) {
            return ResponseEntity.badRequest().body(new ErrorData("User","Usuário não localizado."));
        }

        if (newPost.userEmail() == null || newPost.content() == null){
            return ResponseEntity.badRequest().body(new ErrorData("Nulo","Os campos devem ser preenchidos"));
        }

        var post = new Post(newPost, user.getEmail());
        postRepository.save(post);
        return ResponseEntity.ok().body(post);

    }

    @GetMapping("/{email}")
    public ResponseEntity getPostsbyEmail(@PathVariable String email, @RequestParam(required = false) String content){
        var user = userRepository.findById(email).get();
        var posts = user.getPosts();

        if(user == null) {
            return ResponseEntity.badRequest().body(new ErrorData("User","Usuário não encontrado."));
        }

        if(posts == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Post","Nenhum post adicionado."));
        }

        if(content != null) {
            posts = posts.stream().filter(p -> p.getContent().contains((content))).toList();
            return ResponseEntity.ok().body(posts);
        }


        return  ResponseEntity.ok().body(posts.stream().map(PostsDetail::new).toList());

    }

    @DeleteMapping ("/{email}/{idPost}")
    public ResponseEntity deletePost(@PathVariable String email, @PathVariable UUID idPost){
        var user = userRepository.getByEmail(email);
        var post = postRepository.findById(idPost);

        if(user == null) {
            return ResponseEntity.badRequest().body(new ErrorData("User","Usuário não encontrado."));
        }

        if(post == null){
            return ResponseEntity.badRequest().body(new ErrorData("Post","Recado não encontrado!"));
        }

        postRepository.delete(post.get());

        return ResponseEntity.ok().body(user.getPosts());
    }

    @PutMapping ("/{email}/{idPost}")
    @Transactional
    public ResponseEntity editPost(@PathVariable String email, @PathVariable UUID idPost, @RequestBody EditPost postEdited ){
        var user = userRepository.getByEmail(email);

        if(user == null) {
            return ResponseEntity.badRequest().body(new ErrorData("User","Usuário não encontrado."));
        }

        var optionalPost = user.getPosts().stream().filter(t -> t.getId().equals(idPost)).findAny();


        if(optionalPost == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Post","Post não encontrado."));
        }

        var post = optionalPost.get();
        post.EditPost(postEdited);
        postRepository.save(post);

        return ResponseEntity.ok().body(user.getPosts());
    }

    @PutMapping("/like/{idPost}")
    public ResponseEntity likePost(@PathVariable UUID idPost) {
        var post = postRepository.findById(idPost).get();

        if (post == null) {
            return ResponseEntity.badRequest().body(new ErrorData("post", "Post não encontrado"));
        } else {
            post.setLikes(post.getLikes() + 1);
            postRepository.save(post);
        }

        return ResponseEntity.ok().body(post);
    }

    @PutMapping("/deslike/{idPost}")
    public ResponseEntity deslikePost(@PathVariable UUID idPost) {
        var post = postRepository.findById(idPost).get();

        if (post == null) {
            return ResponseEntity.badRequest().body(new ErrorData("post","Post não encontrado"));
        } else {
            post.setLikes(post.getLikes() - 1);
            postRepository.save(post);
        }

        return ResponseEntity.ok().body(post);
    }
}

