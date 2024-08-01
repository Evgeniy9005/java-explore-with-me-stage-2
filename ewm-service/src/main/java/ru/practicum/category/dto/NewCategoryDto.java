package ru.practicum.category.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


@Data
@RequiredArgsConstructor
public class NewCategoryDto {

    @Size(min = 1,max = 50)
    private final String name;

    private final int id;

}
