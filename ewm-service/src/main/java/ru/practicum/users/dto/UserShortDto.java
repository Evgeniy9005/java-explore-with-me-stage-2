package ru.practicum.users.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class UserShortDto {
    private final Integer id;
    private final String name;
}
