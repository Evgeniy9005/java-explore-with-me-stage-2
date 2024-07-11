package ru.practicum.users.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class UserDto {
    private final int id;
    @NotBlank
    private final String name;
    @Email
    private final String email;
}
