package ru.practicum.admin.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.users.dto.request.NewUserRequest;


import static org.junit.jupiter.api.Assertions.*;

class NewUserRequestTest {

    private NewUserRequest newUserRequest;

    @Test
    void test() {
        newUserRequest = new NewUserRequest("email@email.ru","name");
        assertNotNull(newUserRequest);
        assertEquals("NewUserRequest(email=email@email.ru, name=name)",newUserRequest.toString());
    }

}