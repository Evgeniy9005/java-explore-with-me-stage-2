package ru.practicum.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.NotFoundException;
import ru.practicum.admin.dto.UpdateEventAdminRequest;
import ru.practicum.category.converter.CategoryMapper;
import ru.practicum.category.dao.CategoryRepository;
import ru.practicum.category.model.Category;
import ru.practicum.events.coverter.EventsMapper;
import ru.practicum.events.dao.EventsRepository;
import ru.practicum.events.model.Event;
import ru.practicum.users.converter.UserMapper;
import ru.practicum.users.request.NewUserRequest;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.users.dao.UserRepository;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.model.User;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.util.Util.page;
import static ru.practicum.util.Patch.patchEventAdmin;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private static final String ADMIN = "Admin: ";

    private static final String APP = "ewm-main-service";

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final EventsRepository eventsRepository;

    private final EventsMapper eventsMapper;

    /*Добавление новой категории*/
    @Override
    public CategoryDto addNewCategory(NewCategoryDto newCategoryDto,HttpServletRequest request) {
        Category category = categoryRepository.save(Category.builder()
                .name(newCategoryDto.getName())
                .build());
        log.info("Сохраненная категория {}",category);
        // log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));
        return categoryMapper.toCategoryDto(category);
    }

    /*Удаление категории*/
    @Override
    public void deleteCategory(Integer catId,HttpServletRequest request) {
        categoryRepository.deleteById(catId);
        log.info("Категория {} удалена",catId);
        // log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));
    }


    /*Изменение категории*/
    @Override
    public CategoryDto upCategory(CategoryDto categoryDto, Integer catId, HttpServletRequest request) {
        // log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));

        Category category = Category.builder().id(catId).name(categoryDto.getName()).build();

        log.info("Обновлена категория {}",category);

        return categoryMapper.toCategoryDto(category);
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
    public EventFullDto upEvent(UpdateEventAdminRequest eventAdminRequest, int eventId,HttpServletRequest request) {
        Integer categoryId = eventAdminRequest.getCategory();
        Category category = null;
        if(categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new NotFoundException("Не найдена категория # при обновлении события!", categoryId));
        }
        Event event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Не найдено событие # при обновлении события!",eventId));

        Event upEvent = patchEventAdmin(event,eventAdminRequest,category,eventId);
        log.info("Обновленное событие после патча {}",upEvent);
        Event newEvent = eventsRepository.save(upEvent);
        log.info("Новое, сохраненное событие {}",newEvent);

        return eventsMapper.toEventFullDto(newEvent);
    }


    /*Получение информации о пользователях*/
    @Override
    public List<UserDto> getUsers(List<Integer> ids, int from, int size,HttpServletRequest request) {

        List<UserDto> userDtoList;
        Sort sort = Sort.by(Sort.Direction.ASC,"id");
        Pageable pageable = page(from,size,sort);
        log.info("Входные параметры ibs = {} from = {} size = {}",ids,from,size);

        userDtoList = userRepository.findAllByIdWithPageable(ids,pageable).stream()
                .map(user -> userMapper.toUserDto(user))
                .collect(Collectors.toList());
        log.info("Вернулось запрошенных пользователей {}",userDtoList);
        // log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));
        return userDtoList;
    }

    /*Добавление нового пользователя*/
    @Override
    public UserDto addNewUser(NewUserRequest newUserRequest, HttpServletRequest request) {
        User user = userRepository.save(User.builder()
                .name(newUserRequest.getName())
                .email(newUserRequest.getEmail())
                .build());
        log.info("Создан пользователь {}",user);
        UserDto userDto = userMapper.toUserDto(user);
        log.info("Преобразованный объект пользователя {}",userDto);
        // log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));
        return userDto;
    }

    /*Удаление пользователя*/
    @Override
    public void deleteUser(Integer userId, HttpServletRequest request) {
        userRepository.deleteById(userId);
        log.info("Удален пользователь {}",userId);
      //  log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));

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
