package com.conectia.Conectia.Repositories;

import com.conectia.Conectia.Models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
