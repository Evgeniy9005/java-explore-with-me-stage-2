package ru.practicum.category.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class CategoryDto {

    private final int id;
    @NotBlank
    private final String name;
}
