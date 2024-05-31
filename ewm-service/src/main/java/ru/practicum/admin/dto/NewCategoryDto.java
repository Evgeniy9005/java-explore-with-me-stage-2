package ru.practicum.admin.dto;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RequiredArgsConstructor
@ToString
public class NewCategoryDto {
    @Max(50)
    @Min(1)
    private final String name;

    private final int id;

}
