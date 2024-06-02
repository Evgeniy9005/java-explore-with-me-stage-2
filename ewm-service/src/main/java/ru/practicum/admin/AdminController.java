package ru.practicum.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.admin.dto.NewUserRequest;
import ru.practicum.users.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

import static ru.practicum.stats.Stats.*;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {


   // @Value("${host}")

}
