package com.conectia.Conectia.Repositories;


import com.conectia.Conectia.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    User getByEmail(String email);
}
