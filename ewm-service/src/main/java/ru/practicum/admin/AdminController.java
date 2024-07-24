package ru.practicum.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.dto.UpdateEventAdminRequest;
import ru.practicum.compilations.dto.UpdateCompilationRequest;
import ru.practicum.constants.State;
import ru.practicum.users.request.NewUserRequest;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.events.dto.EventFullDto;
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
    public void deleteCategory(@PathVariable @Positive int catId, HttpServletRequest request) {
        adminService.deleteCategory(catId,request);
    }

    @PatchMapping("/categories/{catId}")
    public CategoryDto upCategory(@RequestBody CategoryDto categoryDto,
                                  @PathVariable Integer catId,
                                  HttpServletRequest request
    ) {
        return adminService.upCategory(categoryDto,catId,request);
    }

    @GetMapping("/events")
    public List<EventFullDto> getEvents(@RequestParam List<Integer> users,
                                        @RequestParam List<State> states,
                                        @RequestParam List<Integer> categories,
                                        @RequestParam String rangeStart,
                                        @RequestParam String rangeEnd,
                                        @RequestParam (defaultValue = "0") int from,
                                        @RequestParam (defaultValue = "10") int size,
                                        HttpServletRequest request
    ) {

        return adminService.getEvents(users,states,categories,rangeStart,rangeEnd,from,size,request);
    }

    @PatchMapping("/events/{eventId}")
    public EventFullDto upEvent(@RequestBody UpdateEventAdminRequest eventAdminRequest,
                                @PathVariable @Positive int eventId,
                                HttpServletRequest request
    ) {

        return adminService.upEvent(eventAdminRequest,eventId,request);
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
        return adminService.addNewUser(newUserRequest,request);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer userId, HttpServletRequest request) {
       adminService.deleteUser(userId,request);
    }

    @PostMapping("/compilations")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CompilationDto addNewCompilation(@RequestBody NewCompilationDto newCompilationDto, HttpServletRequest request) {
        return adminService.addNewCompilation(newCompilationDto,request);
    }

    @DeleteMapping("/compilations/{compId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable @Positive int compId, HttpServletRequest request) {
        adminService.deleteCompilation(compId,request);
    }

    @PatchMapping("/compilations/{compId}")
    public CompilationDto upCompilation(@RequestBody UpdateCompilationRequest ucr,
                                        @PathVariable @Positive int compId,
                                        HttpServletRequest request
    ) {
    return adminService.upCompilation(ucr,compId,request);
    }
}
