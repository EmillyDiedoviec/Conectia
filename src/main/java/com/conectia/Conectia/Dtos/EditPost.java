package com.conectia.Conectia.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditPost(
        @NotBlank
        @NotNull
        String content
) {

}
