package ru.practicum.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.users.dto.request.NewUserRequest;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.model.UpdateEventAdminRequest;
import ru.practicum.users.dto.UserDto;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.stats.Stats.getStatsClient;
import static ru.practicum.stats.Stats.hit;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {

    private static final String ADMIN = "Admin: ";

    private static final String APP = "ewm-main-service";

    private final AdminService adminService;

    @PostMapping("/categories")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CategoryDto addNewCategory(@RequestBody NewCategoryDto newCategoryDto, HttpServletRequest request) {

        return adminService.addNewCategory(newCategoryDto,request);
    }

    @DeleteMapping("/categories/{catId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Integer catId, HttpServletRequest request) {
        adminService.deleteUser(catId,request);
    }

    @PostMapping("/categories/{catId}")
    public CategoryDto upCategory(@RequestBody CategoryDto categoryDto,
                                  @PathVariable Integer catId,
                                  HttpServletRequest request
    ) {
        return adminService.upCategory(categoryDto,catId,request);
    }

    @GetMapping("/events")
    public List<EventFullDto> getEvents(@RequestParam String users,
                                        @RequestParam String states,
                                        @RequestParam String categories,
                                        @RequestParam String rangeStart,
                                        @RequestParam String rangeEnd,
                                        @RequestParam (defaultValue = "0") int from,
                                        @RequestParam (defaultValue = "10") int size,
                                        HttpServletRequest request
    ) {
        log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));
        return null;
    }

    @PostMapping("/events/{eventId}")
    public EventFullDto upEvent(@RequestBody UpdateEventAdminRequest eventAdminRequest,
                                           @PathVariable Integer eventId,
                                           HttpServletRequest request
    ) {
        log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));
        return null;
    }

    @GetMapping("/users")
    public List<UserDto> getUsers(@RequestParam("ids") List<Integer> ids,
                                  @RequestParam (defaultValue = "0") @PositiveOrZero int from,
                                  @RequestParam (defaultValue = "10") @Positive int size,
                                  HttpServletRequest request
    ) {
        return adminService.getUsers(ids,from,size,request);
    }

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDto addNewUser(@RequestBody NewUserRequest newUserRequest, HttpServletRequest request) {
        log.info("{} запрос на добавления пользователя {} ",ADMIN, newUserRequest);
        return adminService.addNewUser(newUserRequest,request);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer userId, HttpServletRequest request) {
       // log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));
       adminService.deleteUser(userId,request);
    }

    @PostMapping("/compilations")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CompilationDto addNewCompilation(@RequestBody NewCompilationDto newCompilationDto, HttpServletRequest request) {

        log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));
        return null;
    }

    @DeleteMapping("/compilations/{compId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Integer compId, HttpServletRequest request) {

    }

    @PatchMapping("/compilations/{compId}")
    public CompilationDto upCompilation(@PathVariable Integer compId, HttpServletRequest request) {
        log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));
    return null;
    }
}
