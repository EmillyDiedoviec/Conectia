package com.conectia.Conectia.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditComment(
        @NotBlank
        @NotNull
        String comment
) {
}
