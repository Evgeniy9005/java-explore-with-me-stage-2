package ru.practicum.compilations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.compilations.converter.CompilationMapper;
import ru.practicum.compilations.dao.CompilationRepository;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.model.Compilation;
import ru.practicum.events.dao.EventsRepository;
import ru.practicum.events.model.Event;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.practicum.stats.Stats.getStatsClient;
import static ru.practicum.stats.Stats.hit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl {

    private static final String COMPILATION = "Compilation: ";

    private static final String APP = "ewm-main-service";

    private final CompilationRepository compilationRepository;

    private final CompilationMapper compilationMapper;

    private final EventsRepository eventsRepository;

    private final ObjectMapper objectMapper;

    //искать только закрепленные/не закрепленные подборки
    public List<CompilationDto> getCompilations(Boolean pinned,
                                                int from,
                                                int size,
                                                HttpServletRequest request
    ) {
        //log.info("{} отправлена статистика {}",COMPILATION,getStatsClient().put(hit(APP,request)));
        List<Compilation> compilationList;
        if(pinned == null) {
            compilationList = compilationRepository.findAll();
            List<Integer> eventIds = new ArrayList<>();
            Map<Compilation,List<Integer>> compilationListMap = new HashMap<>();
            compilationList.stream().map(c -> {
                List<Integer> list = parsingJsonIdEvents(c.getEvents());
                compilationListMap.put(c,list);
                eventIds.addAll(list);
                return list;
            });
         //  List<Integer> integerList = compilationListMap.values().stream().flatMap(List::stream).collect(Collectors.toList());
               List<Event> eventList  =  eventsRepository.findAllById(eventIds);
            return null;
        }
        return null;
    }


    public CompilationDto getCompilation(int compId, HttpServletRequest request) {
        //log.info("{} отправлена статистика {}",COMPILATION,getStatsClient().put(hit(APP,request)));
        return null;
    }

    private List<Integer> parsingJsonIdEvents(String json){
            List<Integer> eventIds = objectMapper.convertValue(json,List.class);
            return eventIds;

    }
}
