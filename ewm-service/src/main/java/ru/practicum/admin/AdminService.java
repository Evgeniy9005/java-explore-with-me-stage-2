package ru.practicum.admin;

import ru.practicum.users.dto.request.NewUserRequest;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.model.UpdateEventAdminRequest;
import ru.practicum.users.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AdminService {
    /**Добавление новой категории*/
    CategoryDto addNewCategory(NewCategoryDto newCategoryDto);
    /**Удаление категории*/
    void deleteCategory(Integer catId);
    /**Изменение категории*/
    CategoryDto upCategory(Integer catId);
    /**Поиск событий*/
    List<EventFullDto> getEvents(String users,
                                        String states,
                                        String categories,
                                        String rangeStart,
                                        String rangeEnd,
                                        int from,
                                        int size
    );
    /**Редактирование данных события и его статуса (отклонение/публикация).*/
    EventFullDto upEvent(UpdateEventAdminRequest eventAdminRequest, Integer eventId);
    /**Получение информации о пользователях*/
    List<UserDto> getUsers(String ibs, int from, int size);
    /**Добавление нового пользователя*/
    UserDto addNewUser(NewUserRequest newUserRequest,HttpServletRequest request);
    /**Удаление пользователя*/
    void deleteUser(Integer userId);
    /**Добавление новой подборки (подборка может не содержать событий)*/
    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto);
    /**Удаление подборки*/
    void deleteCompilation(Integer compId);
    /**Обновить информацию о подборке*/
    CompilationDto upCompilation( Integer compId);
}
