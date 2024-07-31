package ru.practicum.users.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;

@RequiredArgsConstructor
@ToString
@Getter
public class NewUserRequest {


    @Email
    @NotNull
    @Size(min = 6, max = 254)
    private final String email;
    @NotBlank
    @Size(min = 2, max = 250)
    private final String name;
}
