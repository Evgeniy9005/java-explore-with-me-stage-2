package ru.practicum.users.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    private UserDto userDto = new UserDto(1,"User","email@email.ru");

    @Test
    void testUserDto() {
        assertEquals("UserDto(email=email@email.ru, id=1, name=User)",userDto.toString());
    }
}