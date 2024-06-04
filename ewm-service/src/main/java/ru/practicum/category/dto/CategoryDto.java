package ru.practicum.category.dto;

import lombok.RequiredArgsConstructor;
import lombok.ToString;





@ToString
@RequiredArgsConstructor
public class CategoryDto {
    private final int id;
    private final String name;
}
