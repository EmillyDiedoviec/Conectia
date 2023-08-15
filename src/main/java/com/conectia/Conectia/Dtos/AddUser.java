package com.conectia.Conectia.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record AddUser(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
//        @NotBlank
//        String repassword,
        @NotBlank
        String name,
        @NotBlank
        String born

)
{}
