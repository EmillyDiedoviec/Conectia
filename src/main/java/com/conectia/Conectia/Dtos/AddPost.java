package com.conectia.Conectia.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddPost (
        @NotBlank
        @NotNull
        String content,
        @NotNull
        String userEmail
){

}
