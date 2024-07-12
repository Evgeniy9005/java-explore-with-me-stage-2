package ru.practicum.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static ru.practicum.stats.Stats.getStatsClient;
import static ru.practicum.stats.Stats.hit;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {
    private static final String CATEGORY = "Category: ";

    private static final String APP = "ewm-main-service";

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam (defaultValue = "0") int from,
                                           @RequestParam (defaultValue = "10") int size,
                                           HttpServletRequest request
    ) {
        log.info("{} запрос на получение списка категорий от {} до {}",CATEGORY,from,size);
       // log.info("{} отправлена статистика {}",CATEGORY,getStatsClient().put(hit(APP,request)));
        return null;
    }

    @GetMapping("{catId}")
    public CategoryDto getCategory(@PathVariable Integer catId,
                                           HttpServletRequest request
    ) {
        log.info("{} запрос на получение категории по id {}",CATEGORY, catId);
      //  log.info("{} отправлена статистика {}",CATEGORY,getStatsClient().put(hit(APP,request)));
        return null;
    }
}
