package ru.practicum.stats;


import dto.EndpointHitDto;
import dto.ViewStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dao.StatsRepository;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.EndpointHitMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.practicum.BadRequestException;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatsServiceImpl implements StatsService {

    private final StatsRepository repository;

    private final EndpointHitMapper mapper;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    public EndpointHitDto addStats(EndpointHitDto endpointHitDto) {
        EndpointHit save = repository.save(mapper.toEndpointHit(endpointHitDto));
        log.info("Добавлена статистика {}",save);
        return mapper.toEndpointHitDto(save);
    }

    public List<ViewStats> getStats(String start, String end, String uris, Boolean unique) {
        LocalDateTime timeStart;
        LocalDateTime timeEnd;
        List<String> urisList =  new ArrayList<>();
        List<ViewStats> viewStatsList = null;

        if (start != null || start.isBlank()) {
            timeStart = LocalDateTime.parse(start, formatter);
        } else {
            timeStart = LocalDateTime.now();
        }

        if (end != null || end.isBlank()) {
            timeEnd = LocalDateTime.parse(end, formatter);
        } else {
            timeEnd = LocalDateTime.now();
        }

        if (timeStart.compareTo(timeEnd) > 0) {
            throw new BadRequestException("Время начала # выборки не может быть больше времени конца # выборки ",timeStart, timeEnd);
        }

        log.info("Запрос на статистику start = {}, end = {}, uris = {}, unique = {}",start,end,uris,unique);

        if (uris != null) {
            urisList = Arrays.asList(uris.split(","));
        }

        if (unique != null && unique) { //с учетом уникальности ip пользователя
            if (uris != null) {
                viewStatsList = repository.getCountHitUnique(timeStart,timeEnd,urisList);
                log.info("Выборка статистики с учетом уникальности IP пользователя на каждый элемент массива {}," +
                        " идентификатора регистрированной статистики, в диапазоне времени от {} до {}",urisList, timeStart, timeEnd);
            } else {
                viewStatsList = repository.getCountHitUnique(timeStart,timeEnd);
                log.info("Выборка статистики с учетом уникальности IP пользователя, в диапазоне времени от {} до {}", timeStart, timeEnd);
            }
        }

        if (unique != null && !unique) { //без учета уникальности ip пользователя
            if (uris != null) {
                viewStatsList = repository.getCountHit(timeStart, timeEnd, urisList);
                log.info("Выборка статистики с количеством запросов от клиента на каждый элемент массива {}," +
                        " идентификатора регистрированной статистики, в диапазоне времени от {} до {}",urisList, timeStart, timeEnd);
            } else {
                viewStatsList = repository.getCountHit(timeStart, timeEnd);
                log.info("Выборка статистики с количеством запросов от клиента, в диапазоне времени от {} до {}",timeStart, timeEnd);
            }
        }

        log.info("Вернулась статистика {}",viewStatsList);
        return viewStatsList;
    }

}
