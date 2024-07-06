package ru.practicum.users.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserShortDtoTest {

    private UserShortDto userShortDto = UserShortDto.builder().id(1).name("name").build();

    @Test
    void getId() {
        assertEquals(1,userShortDto.getId());
    }

    @Test
    void getName() {
        assertEquals("name",userShortDto.getName());
    }
}