package com.conectia.Conectia.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddComment(
        @NotBlank
        @NotNull
        String comment,
        @NotNull
        String userEmail,
        @NotNull
        UUID idPost
){

}
