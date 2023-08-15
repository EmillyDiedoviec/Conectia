package com.conectia.Conectia.Repositories;


import com.conectia.Conectia.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    User getByEmail(String email);

    UserDetails findByEmail(String email);
}
