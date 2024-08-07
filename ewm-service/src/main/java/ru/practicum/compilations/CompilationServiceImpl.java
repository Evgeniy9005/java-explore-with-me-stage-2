package ru.practicum.compilations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ConflictException;
import ru.practicum.NotFoundException;
import ru.practicum.compilations.converter.CompilationMapper;
import ru.practicum.compilations.dao.CompilationRepository;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.model.Compilation;
import ru.practicum.events.dao.EventsRepository;
import ru.practicum.events.model.Event;
import ru.practicum.util.Util;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private static final String COMPILATION = "Compilation: ";

    private static final String APP = "ewm-main-service";

    private final CompilationRepository compilationRepository;

    private final CompilationMapper compilationMapper;

    private final EventsRepository eventsRepository;

    private final ObjectMapper objectMapper;

    //искать только закрепленные/не закрепленные подборки
    @Override
    public List<CompilationDto> getCompilations(Boolean pinned,
                                                int from,
                                                int size,
                                                HttpServletRequest request
    ) {
        //log.info("{} отправлена статистика {}",COMPILATION,getStatsClient().put(hit(APP,request)));
        log.info("Входные параметры при получении подборок pinned = {}, from = {}, size = {}",pinned,from,size);
        List<Compilation> compilationList;
        List<CompilationDto> compilationDtoList = new LinkedList<>();
        Map<Compilation,List<Integer>> compilationListMap = new HashMap<>();
        List<Integer> eventIds = new ArrayList<>();
        Pageable pageable = Util.page(from,size);

        if(pinned == null) { //вернуть все подряд
            compilationList = compilationRepository.findAll(pageable).toList();
            log.info("Получены подборки в размере {}!",compilationList.size());
        } else {
            compilationList = compilationRepository.findByPinned(pinned,pageable);
            log.info("Получены подборки в размере {}!",compilationList.size());
        }

        if(compilationList.isEmpty()) {
            return compilationDtoList;
        }


       compilationList.stream().forEach(c -> {
           List<Integer> list = parsingJsonIdEvents(c.getEvents());
           compilationListMap.put(c, list);
           eventIds.addAll(list);
           log.info("Список id событий {} на подборку {}", eventIds,c.getId());
       });

        log.info("Получение compilationListMap со значениями в размере {}",compilationListMap.size());
        // List<Integer> integerList = compilationListMap.values().stream().flatMap(List::stream).collect(Collectors.toList());

        List<Event> eventList = eventsRepository.findAllById(eventIds);
        log.info("Полученные события в количестве {}!",eventList);
        Map<Integer,Event> listEventMap = eventList.stream()
                .collect(Collectors.toMap(event -> event.getId(),event -> event));
        log.info("Получили listEventMap количеством значений {}!",listEventMap.size());

        for (Map.Entry<Compilation,List<Integer>> entry : compilationListMap.entrySet()) {
            Compilation key = entry.getKey();
            List<Integer> value = entry.getValue();
            log.info("Значения в цикле получения CompilationDto! key = {}, value = {}",key,value);
            CompilationDto compilationDto = compilationMapper.toCompilationDto(key,value.stream()
                    .map(id -> listEventMap.get(id))
                    .collect(Collectors.toList()));
            compilationDtoList.add(compilationDto);
        }

        compilationDtoList.stream().forEach(c -> log.info(c.toString()));
        return compilationDtoList;
    }

    @Override
    public CompilationDto getCompilation(int compId, HttpServletRequest request) {
        //log.info("{} отправлена статистика {}",COMPILATION,getStatsClient().put(hit(APP,request)));
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Не найдена подборка под id = {}!",compId));
        log.info("Получена подборка {}!",compilation);
        
        List<Integer> eventIds = parsingJsonIdEvents(compilation.getEvents());

        List<Event> eventList = eventsRepository.findAllById(eventIds);

        CompilationDto compilationDto = compilationMapper.toCompilationDto(compilation,eventList);

        log.info("Получена подборка {}!",compilationDto);

        return compilationDto;
    }

    private List<Integer> parsingJsonIdEvents(String json){
        List<Integer> eventIds = new ArrayList<>();

        if(json == null){
            return eventIds;
        }

        log.info("json для десериализации = {}",json);

        try {
            eventIds = objectMapper.readValue(json,new TypeReference<List<Integer>>(){});
        } catch (JsonProcessingException e) {
            throw new ConflictException("Ошибка десериализации из # в List<Integer>!",json);
        }

        return eventIds;
    }
}
