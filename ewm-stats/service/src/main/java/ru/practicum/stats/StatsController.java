package ru.practicum.stats;

import dto.EndpointHitDto;
import dto.ViewStats;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class StatsController {
    private final StatsService service;

    @PostMapping("/hit")
    public ResponseEntity<EndpointHitDto> addStats(@RequestBody EndpointHitDto endpointHitDto) {
        return ResponseEntity.status(201).body(service.addStats(endpointHitDto));
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam (required = false) String uris,
            @RequestParam (defaultValue = "false") Boolean unique
    ) {
        return service.getStats(start,end,uris,unique);
    }
}
