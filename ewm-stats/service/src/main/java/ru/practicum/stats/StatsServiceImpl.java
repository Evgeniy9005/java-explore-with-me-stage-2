package ru.practicum.stats;


import dto.EndpointHitDto;
import dto.ViewStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.dao.StatsRepository;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.EndpointHitMapper;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatsServiceImpl implements StatsService {

    private final StatsRepository repository;

    private final EndpointHitMapper mapper;

  /*  @Autowired
    public StatsServiceImpl(StatsRepository repository, EndpointHitMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }*/

    public EndpointHitDto addStats(EndpointHitDto endpointHitDto) {
        EndpointHit save = repository.save(mapper.toEndpointHit(endpointHitDto));
        log.info("Добавлена статистика {}",save);
        return mapper.toEndpointHitDto(save);
    }

    public List<ViewStats> getStats(String start, String end, String uris, String unique) {

        log.info("Запрос на статистику start = {}, end = {}, uris = {}, unique = {}",start,end,uris,unique);

        List<EndpointHit> endpointHitList =  repository.findAll();

        List<ViewStats> viewStatsList = repository.getCountHit();

        log.info("Вернулась статистика {}",viewStatsList);

        return viewStatsList;
    }
}
