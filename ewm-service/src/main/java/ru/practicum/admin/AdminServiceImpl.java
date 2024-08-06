package ru.practicum.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ConflictException;
import ru.practicum.NotFoundException;
import ru.practicum.admin.dto.UpdateEventAdminRequest;
import ru.practicum.category.converter.CategoryMapper;
import ru.practicum.category.dao.CategoryRepository;
import ru.practicum.category.model.Category;
import ru.practicum.compilations.converter.CompilationMapper;
import ru.practicum.compilations.dao.CompilationRepository;
import ru.practicum.compilations.dto.UpdateCompilationRequest;
import ru.practicum.compilations.model.Compilation;
import ru.practicum.constants.State;
import ru.practicum.events.coverter.EventsMapper;
import ru.practicum.events.dao.EventsRepository;
import ru.practicum.events.dto.EventShortDto;
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
import ru.practicum.util.Patch;
import ru.practicum.util.Util;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private final CompilationRepository compilationRepository;

    private final CompilationMapper compilationMapper;

    private final ObjectMapper objectMapper;

    /*Добавление новой категории*/
    @Override
    public CategoryDto addNewCategory(NewCategoryDto newCategoryDto,HttpServletRequest request) {
        Category category;
        log.info("Входные параметры при добавлении категории! newCategoryDto = {}",newCategoryDto);
        //try {
            category = categoryRepository.save(Category.builder()
                    .name(newCategoryDto.getName())
                    .build());
        /*} catch (DataIntegrityViolationException e) {
            throw new ConflictException("Добавление новой категории с занятым именем #!",newCategoryDto.getName());
        }*/

        // log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));
        CategoryDto categoryDto = categoryMapper.toCategoryDto(category);

        log.info("Сохраненная категория {}",categoryDto);

        return categoryDto;
    }

    /*Удаление категории*/
    @Override
    public void deleteCategory(int catId,HttpServletRequest request) {
       // try {
            categoryRepository.deleteById(catId);
       /* } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Удаление категории # с привязанными событиями !",catId);
        }*/

        log.info("Категория {} удалена",catId);
        // log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));
    }


    /*Изменение категории*/
    @Override
    public CategoryDto upCategory(CategoryDto categoryDto, Integer catId, HttpServletRequest request) {
        // log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));
        Category upCategory;
        Category category = Category.builder().id(catId).name(categoryDto.getName()).build();

       // try {
            upCategory = categoryRepository.save(category);
       /* } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Попытка изменения имени # категории на уже существующее!",category.getName());
        }*/

        CategoryDto newCategoryDto = categoryMapper.toCategoryDto(upCategory);

        log.info("Обновлена категория {}",newCategoryDto);

        return newCategoryDto;
    }

    /*Поиск событий*/
    @Override
    public List<EventFullDto> getEvents(List<Integer> users,
                                        List<State> states,
                                        List<Integer> categories,
                                        String rangeStart,
                                        String rangeEnd,
                                        int from,
                                        int size,
                                        HttpServletRequest request
    ) {
        List<Event> events;

        log.info("Поиск событий. Входные параметры users = {}, " +
                "states = {}, categories = {}, rangeStart = {}, " +
                "rangeEnd = {}, from = {}, size = {}",users,states,categories,rangeStart,rangeEnd,from,size);

        // log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));

        Sort sort = Sort.by(Sort.Direction.ASC,"id");


            events = eventsRepository.getEvents(users,
                    states,
                    categories,
                    Util.getDateStart(rangeStart),
                    Util.getDateEnd(rangeEnd),
                    Util.page(from,size,sort)
            );


        log.info("Полученные события в количестве {}:",events.size());
        List<EventFullDto> eventFullDtos = events.stream()
                .map(event -> eventsMapper.toEventFullDto(event))
                .collect(Collectors.toList());

        eventFullDtos.stream().forEach(eventfullDto -> log.info(eventfullDto.toString()));

        return eventFullDtos;
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

        if(ids != null) {
            userDtoList = userRepository.findAllByIdWithPageable(ids,pageable).stream()
                    .map(user -> userMapper.toUserDto(user))
                    .collect(Collectors.toList());
        } else {
            userDtoList = userRepository.findAll(pageable).stream()
                    .map(user -> userMapper.toUserDto(user))
                    .collect(Collectors.toList());
        }

        log.info("Вернулось запрошенных пользователей {}",userDtoList);
        // log.info("{} отправлена статистика {}",ADMIN,getStatsClient().put(hit(APP,request)));
        return userDtoList;
    }

    /*Добавление нового пользователя*/
    @Override
    public UserDto addNewUser(NewUserRequest newUserRequest, HttpServletRequest request) {
        log.info("{} Запрос на добавления пользователя {} ",ADMIN, newUserRequest);
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
    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto,HttpServletRequest request) {
        log.info("Входные параметры при добавлении подборки событий newCompilationDto = {}",newCompilationDto);
        List<Event> eventList = new ArrayList<>();
        List<Integer> eventIds = newCompilationDto.getEvents();
        String json = "[]";

        if (eventIds != null) {
            eventList = eventsRepository.findAllById(eventIds);
            try {
                json = objectMapper.writeValueAsString(eventIds);
            } catch (JsonProcessingException e) {
                throw new ConflictException("Ошибка преобразования данных #",eventIds);
            }
        }

        Compilation compilation = Compilation.builder()
                .events(json)
                .pinned(newCompilationDto.isPinned())
                .title(newCompilationDto.getTitle())
                .build();

        Compilation newCompilation = compilationRepository.save(compilation);

        CompilationDto compilationDto = compilationMapper.toCompilationDto(newCompilation,eventList);
        log.info("Добавлена подборка событий {}",compilationDto);
        return compilationDto;
    }

    /*Удаление подборки*/
    @Override
    public void deleteCompilation(int compId,HttpServletRequest request) {
        compilationRepository.deleteById(compId);
        log.info("Подборка событий под id = {} удалена!",compId);
    }

    /*Обновить информацию о подборке*/
    @Override
    public CompilationDto upCompilation(UpdateCompilationRequest ucr, int compId, HttpServletRequest request) {
        log.info("Входные параметры при обновлении подборки compId = {}, ucr {}",compId,ucr);

        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Не найдена подборка # при обновлении!",compId));

        Object[] result = Patch.patchCompilation(compilation,ucr);
        Compilation patchCompilation = (Compilation) result[0];
        List<Integer> eventIds = (List<Integer>) result[1];
        log.info("Обновленная подборка после patchCompilation! patchCompilation = {}, eventIds = {}",patchCompilation,eventIds);

        Compilation upCompilation = compilationRepository.save(patchCompilation);

        CompilationDto compilationDto = compilationMapper.toCompilationDto(upCompilation,eventsRepository.findAllById(eventIds));
        log.info("Обновлена подборка {}, {}",compId,compilationDto);
        return compilationDto;
    }
}
