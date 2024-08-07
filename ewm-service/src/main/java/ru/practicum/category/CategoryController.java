package ru.practicum.category;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.util.List;



@Validated
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam (defaultValue = "0") int from,
                                           @RequestParam (defaultValue = "10") int size,
                                           HttpServletRequest request
    ) {
        return categoryService.getCategories(from,size,request);
    }

    @GetMapping("{catId}")
    public CategoryDto getCategory(@PathVariable @Positive int catId, HttpServletRequest request) {

        return categoryService.getCategory(catId,request);
    }
}
