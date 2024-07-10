package ru.practicum.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.users.dto.request.NewUserRequest;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.model.UpdateEventAdminRequest;
import ru.practicum.users.dao.UserRepository;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static ru.practicum.stats.Stats.getStatsClient;
import static ru.practicum.stats.Stats.hit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private static final String ADMIN = "Admin: ";

    private static final String APP = "ewm-main-service";

    private final UserRepository userRepository;

    /*Добавление новой категории*/
    @Override
    public CategoryDto addNewCategory(NewCategoryDto newCategoryDto) {

        return null;
    }

    /*Удаление категории*/
    @Override
    public void deleteCategory(Integer catId) {

    }


    /*Изменение категории*/
    @Override
    public CategoryDto upCategory(Integer catId) {

        return null;
    }

    /*Поиск событий*/
    @Override
    public List<EventFullDto> getEvents(String users,
                                        String states,
                                        String categories,
                                        String rangeStart,
                                        String rangeEnd,
                                        int from,
                                        int size
    ) {
        return null;
    }

    /*Редактирование данных события и его статуса (отклонение/публикация).*/
    @Override
    public EventFullDto upEvent(UpdateEventAdminRequest eventAdminRequest, Integer eventId) {

        return null;
    }


    /*Получение информации о пользователях*/
    @Override
    public List<UserDto> getUsers(String ibs, int from, int size) {

        return null;
    }

    /*Добавление нового пользователя*/
    @Override
    public UserDto addNewUser(NewUserRequest newUserRequest, HttpServletRequest request) {
        User user = userRepository.save(User.builder()
                .name(newUserRequest.getName())
                .email(newUserRequest.getEmail())
                .build());
        log.info("Создан пользователь {}",user);
       // log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));
        return null;
    }

    /*Удаление пользователя*/
    @Override
    public void deleteUser(Integer userId) {
    }


    /*Добавление новой подборки (подборка может не содержать событий)*/
    @Override
    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto) {

        return null;
    }

    /*Удаление подборки*/
    @Override
    public void deleteCompilation(Integer compId) {

    }

    /*Обновить информацию о подборке*/
    @Override
    public CompilationDto upCompilation( Integer compId) {
        return null;
    }
}
