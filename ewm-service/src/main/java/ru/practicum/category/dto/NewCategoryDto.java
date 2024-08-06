package ru.practicum.category.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@RequiredArgsConstructor
public class NewCategoryDto {

    @NotBlank
    @Size(min = 1,max = 50)
    private final String name;

    private final int id;

}
