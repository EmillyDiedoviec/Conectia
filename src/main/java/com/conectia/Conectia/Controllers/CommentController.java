package com.conectia.Conectia.Controllers;

import com.conectia.Conectia.Dtos.*;
import com.conectia.Conectia.Models.Comment;
import com.conectia.Conectia.Models.Post;
import com.conectia.Conectia.Repositories.CommentRepository;
import com.conectia.Conectia.Repositories.PostRepository;
import com.conectia.Conectia.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    @PostMapping("/{idPost}")
    public ResponseEntity addComment(@PathVariable @NotNull UUID idPost, @RequestBody @NotNull @Valid AddComment newComment){
        var user = userRepository.findById(newComment.userEmail()).get();
        var post = postRepository.findById(newComment.idPost()).get();

        if(user == null) {
            return ResponseEntity.badRequest().body(new ErrorData("User","Usuário não encontrado."));
        }

        if(post == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Post","Post não encontrado."));
        }

        var comment = new Comment(newComment, user.getEmail(), post.getId());
        commentRepository.save(comment);
        return ResponseEntity.ok().body(newComment);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity getCommentsUser(@PathVariable String email){
        var user = userRepository.findById(email).get();
        var comments = user.getComments();

        if(comments == null) {
            return ResponseEntity.badRequest().body(new ErrorData("comment","Nenhum comentário adicionado."));
        }

        return  ResponseEntity.ok().body(comments.stream().map(CommentDetail::new).toList());
    }

    @GetMapping("/post/{idPost}")
    public ResponseEntity getCommentsPost(@PathVariable UUID idPost){
        var post = postRepository.findById(idPost).get();
        var comments = post.getComments();

        if(comments == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Comment","Nenhum comnetário adicionado nesse post."));
        }

        return  ResponseEntity.ok().body(comments.stream().map(CommentDetail::new).toList());
    }

    @DeleteMapping ("/{email}/{idComment}")
    public ResponseEntity deleteComment(@PathVariable String email, @PathVariable UUID idComment){
        var user = userRepository.findById(email);
        var commentOptional = commentRepository.findById(idComment);

        if(commentOptional == null){
            return ResponseEntity.badRequest().body(new ErrorData("post","Recado não encontrado!"));
        }

        commentOptional.ifPresent(comment -> {
            commentRepository.delete(comment);
        });

        return ResponseEntity.ok().body(user);
    }

    @PutMapping ("/{email}/{idComment}")
    @Transactional
    public ResponseEntity editComment(@PathVariable String email, @PathVariable UUID idComment, @RequestBody EditComment commentEdited ){
        var user = userRepository.getByEmail(email);
        var comment = user.getComments().stream().filter(t -> t.getId_comment().equals(idComment)).findAny();


        var comment1 = comment.get();
        comment1.EditComment(commentEdited);
        commentRepository.save(comment1);

        return ResponseEntity.ok().body(comment);
    }

    @PutMapping("/like/{idComment}")
    public ResponseEntity likePost(@PathVariable UUID idComment) {
        var comment = commentRepository.findById(idComment).get();

        if (comment == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Comment","Comentário não encontrado"));
        } else {
            comment.setLikes(comment.getLikes() + 1);
            commentRepository.save(comment);
        }

        return ResponseEntity.ok().body(comment);
    }

    @PutMapping("/deslike/{idComment}")
    public ResponseEntity deslikePost(@PathVariable UUID idComment) {
        var comment = commentRepository.findById(idComment).get();

        if (comment == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Comment","Post não encontrado"));
        } else {
            comment.setLikes(comment.getLikes() - 1);
            commentRepository.save(comment);
        }

        return ResponseEntity.ok().body(comment);
    }
}

