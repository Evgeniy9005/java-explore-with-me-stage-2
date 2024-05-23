package ru.practicum.stats;

import dto.EndpointHitDto;
import dto.ViewStats;
import ru.practicum.model.EndpointHit;

public interface StatsService {

   EndpointHitDto addStats(EndpointHitDto endpointHitDto);

   ViewStats getStats(String start, String end, String uris, String unique);


}
