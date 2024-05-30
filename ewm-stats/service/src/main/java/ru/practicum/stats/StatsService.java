package ru.practicum.stats;

import dto.EndpointHitDto;
import dto.ViewStats;
import ru.practicum.model.EndpointHit;

import java.util.List;

public interface StatsService {

   EndpointHitDto addStats(EndpointHitDto endpointHitDto);

   List<ViewStats> getStats(String start, String end, String uris, Boolean unique);

}
