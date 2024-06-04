package ru.practicum.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import javax.servlet.http.HttpServletRequest;

import static ru.practicum.stats.Stats.getStatsClient;
import static ru.practicum.stats.Stats.hit;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class CategoryController {
    private static final String ADMIN = "Admin: ";

    @PostMapping("/admin/categories")
    public CategoryDto addNewCategory(@RequestBody NewCategoryDto newCategoryDto, HttpServletRequest request) {
        log.info("{} запрос на добавления категории {} ",ADMIN, newCategoryDto);
        log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit("ewm-main-service",request)));
        return null;
    }

}
