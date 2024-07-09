package ru.practicum.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.dto.NewUserRequest;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.model.UpdateEventAdminRequest;
import ru.practicum.users.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static ru.practicum.stats.Stats.getStatsClient;
import static ru.practicum.stats.Stats.hit;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    /*Добавление новой категории*/
    public CategoryDto addNewCategory(NewCategoryDto newCategoryDto) {

        return null;
    }

    /*Удаление категории*/
    public void deleteCategory(Integer catId) {

    }

    /*Изменение категории*/
    public CategoryDto upCategory(Integer catId) {

        return null;
    }

    /*Поиск событий*/
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
    public EventFullDto upEvent(UpdateEventAdminRequest eventAdminRequest, Integer eventId) {

        return null;
    }


    /*Получение информации о пользователях*/
    public List<UserDto> getUsers(String ibs, int from, int size) {

        return null;
    }

    /*Добавление нового пользователя*/
    public UserDto addNewUser(NewUserRequest newUserRequest) {
        return null;
    }


    /*Удаление пользователя*/
    public void deleteUser(Integer userId, HttpServletRequest request) {
    }


    /*Добавление новой подборки (подборка может не содержать событий)*/
    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto) {

        return null;
    }

    /*Удаление подборки*/
    public void deleteCompilation(Integer compId, HttpServletRequest request) {

    }

    /*Обновить информацию о подборке*/
    public CompilationDto upCompilation( Integer compId, HttpServletRequest request) {
        return null;
    }
}
