package com.ecommerce.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequestDTO(

        // Aqui se realiza la validacion de los campos, al ser los campos que vamos a enviar a
        // la base de datos se requiere que sean validos.
        @NotBlank(message = "the product name can't be empty")
        String name,
        String description,
        @NotNull(message = "the price is mandatory.")
        @Positive(message = "The price must be greater than zero.")
        BigDecimal price

) {
}
