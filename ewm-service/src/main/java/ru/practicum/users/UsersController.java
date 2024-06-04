package ru.practicum.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.admin.dto.NewUserRequest;
import ru.practicum.users.dto.UserDto;
import javax.servlet.http.HttpServletRequest;

import static ru.practicum.stats.Stats.getStatsClient;
import static ru.practicum.stats.Stats.hit;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class UsersController {
    private static final String ADMIN = "Admin: ";

    @PostMapping("/admin/users")
    public UserDto addNewUser(@RequestBody NewUserRequest newUserRequest, HttpServletRequest request) {
        log.info("{} запрос на добавления пользователя {} ",ADMIN, newUserRequest);
        log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit("ewm-main-service",request)));
        return null;
    }
}
