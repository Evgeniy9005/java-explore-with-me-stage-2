package ru.practicum.users.dto;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@ToString
@RequiredArgsConstructor
public class UserDto {
    @Email
    private final String email;

    private final int id;

    @NotBlank
    private final String name;

}
