package ru.practicum.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.admin.dto.CategoryDto;
import ru.practicum.admin.dto.NewCategoryDto;
import ru.practicum.admin.dto.NewUserRequest;
import ru.practicum.admin.dto.UserDto;
import ru.practicum.client.StatsClient;

import javax.servlet.http.HttpServletRequest;

import static ru.practicum.stats.Stats.*;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {


   // @Value("${host}")
    private static final String ADMIN = "Admin: ";


    @PostMapping("/users")
    public UserDto addNewUser(@RequestBody NewUserRequest newUserRequest, HttpServletRequest request) {
        log.info("{} запрос на добавления пользователя {} ",ADMIN, newUserRequest);
        log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit("ewm-main-service",request)));
        return null;
    }
    @PostMapping("/categories")
    public CategoryDto addNewCategory(@RequestBody NewCategoryDto newCategoryDto, HttpServletRequest request) {
        log.info("{} запрос на добавления категории {} ",ADMIN, newCategoryDto);
        log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit("ewm-main-service",request)));
        return null;
    }
}
