package ru.practicum.admin.dto;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RequiredArgsConstructor
@ToString
public class NewUserRequest {
    @Email
    @Max(254)
    @Min(6)
    private final String email;
    @Max(250)
    @Min(2)
    private final String name;
}
