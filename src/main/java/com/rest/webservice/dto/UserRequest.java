package com.rest.webservice.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserRequest(@NotNull(message = "Name shouldn't be null") String name, @NotNull(message = "DOB shouldn't be null") LocalDate date) {
}
