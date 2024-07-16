package ru.practicum.events.model;

import lombok.*;

/**
 * <p>Широта - lat</p>
 * <p>Долгота - lon</p>
 * */

@Data
@RequiredArgsConstructor
public class Location {
    /**Широта*/
    private final float lat;
    /**Долгота*/
    private final float lon;
}
