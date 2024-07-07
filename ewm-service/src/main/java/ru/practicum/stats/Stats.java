package ru.practicum.stats;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.EndpointHitDto;
import ru.practicum.client.StatsClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Stats {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static StatsClient statsClient = new StatsClient("localhost","9090");

    public static StatsClient getStatsClient() {
        return statsClient;
    }

    public static String hit(String app, HttpServletRequest request) {
        String data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        EndpointHitDto ehd = EndpointHitDto.builder()
                .app(app)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(data)
                .build();
        String stats;
        try {
            stats = objectMapper.writeValueAsString(ehd);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return stats;
    }
}
