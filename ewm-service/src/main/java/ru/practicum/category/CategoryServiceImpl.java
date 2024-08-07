package ru.practicum.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.NotFoundException;
import ru.practicum.category.converter.CategoryMapper;
import ru.practicum.category.dao.CategoryRepository;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.util.Util;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.stats.Stats.getStatsClient;
import static ru.practicum.stats.Stats.hit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private static final String CATEGORY = "Category: ";

    private static final String APP = "ewm-main-service";

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getCategories( int from, int size, HttpServletRequest request) {
        log.info("{} отправлена статистика {}",CATEGORY,getStatsClient().put(hit(APP,request)));
        log.info("{} запрос на получение списка категорий от {} до {}",CATEGORY,from,size);
        List<CategoryDto> categoryDtoList = categoryRepository.findAll(Util.page(from,size))
                .stream().map(category -> categoryMapper.toCategoryDto(category)).collect(Collectors.toList());
        log.info("Получен список категорий {}",categoryDtoList);
        return categoryDtoList;
    }

    @Override
    public CategoryDto getCategory(Integer catId, HttpServletRequest request) {
        log.info("{} отправлена статистика {}",CATEGORY,getStatsClient().put(hit(APP,request)));
        log.info("{} запрос на получение категории по id {}",CATEGORY, catId);
        Category category = categoryRepository.findById(catId)
                .orElseThrow(()-> new NotFoundException("Не найдена категория под id = #",catId));
        log.info("Получена категория {}",category);
        return categoryMapper.toCategoryDto(category);
    }
}
