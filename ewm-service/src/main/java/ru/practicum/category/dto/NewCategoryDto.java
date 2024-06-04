package ru.practicum.category.dto;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;




@ToString
@RequiredArgsConstructor
public class NewCategoryDto {

    @Max(50)
    @Min(1)
    private final String name;

    private final int id;

}
