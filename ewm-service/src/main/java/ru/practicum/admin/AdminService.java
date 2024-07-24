package ru.practicum.admin;

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
import java.util.List;

public interface AdminService {
    /**Добавление новой категории*/
    CategoryDto addNewCategory(NewCategoryDto newCategoryDto,HttpServletRequest request);
    /**Удаление категории*/
    void deleteCategory(int catId,HttpServletRequest request);
    /**Изменение категории*/
    CategoryDto upCategory(CategoryDto categoryDto,Integer catId,HttpServletRequest request);
    /**Поиск событий*/
    List<EventFullDto> getEvents(List<Integer> users,
                                 List<State> states,
                                 List<Integer> categories,
                                        String rangeStart,
                                        String rangeEnd,
                                        int from,
                                        int size,
                                 HttpServletRequest request
    );
    /**Редактирование данных события и его статуса (отклонение/публикация).*/
    EventFullDto upEvent(UpdateEventAdminRequest eventAdminRequest, int eventId,HttpServletRequest request);
    /**Получение информации о пользователях*/
    List<UserDto> getUsers(List<Integer> ids, int from, int size,HttpServletRequest request);
    /**Добавление нового пользователя*/
    UserDto addNewUser(NewUserRequest newUserRequest,HttpServletRequest request);
    /**Удаление пользователя*/
    void deleteUser(Integer userId, HttpServletRequest request);
    /**Добавление новой подборки (подборка может не содержать событий)*/
    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto,HttpServletRequest request);
    /**Удаление подборки*/
    void deleteCompilation(int compId,HttpServletRequest request);
    /**Обновить информацию о подборке*/
    CompilationDto upCompilation(UpdateCompilationRequest ucr, int compId, HttpServletRequest request);
}
