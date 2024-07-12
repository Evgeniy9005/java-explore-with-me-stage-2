package ru.practicum.events.model;

import lombok.*;

@Data
@RequiredArgsConstructor
public class Location {
    /**Широта*/
    private final float lat;
    /**Долгота*/
    private final float lon;
}
