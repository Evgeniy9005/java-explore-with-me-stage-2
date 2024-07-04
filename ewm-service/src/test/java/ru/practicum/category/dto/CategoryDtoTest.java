package ru.practicum.category.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDtoTest {

    private CategoryDto categoryDto = new CategoryDto(1,"Категория");

    @Test
    void getId() {
        assertEquals(1,categoryDto.getId());
    }

    @Test
    void getName() {
        assertEquals("Категория",categoryDto.getName());
    }
}