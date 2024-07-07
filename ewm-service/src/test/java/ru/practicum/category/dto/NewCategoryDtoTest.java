package ru.practicum.category.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewCategoryDtoTest {

    private NewCategoryDto newCategoryDto = new NewCategoryDto("name",1);

    @Test
    void test() {
        assertEquals(1,newCategoryDto.getId());
        assertEquals("name",newCategoryDto.getName());
        assertEquals("NewCategoryDto(name=name, id=1)",newCategoryDto.toString());
    }
}