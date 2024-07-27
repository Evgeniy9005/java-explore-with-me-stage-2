package ru.practicum.users.request.model;

import lombok.*;

@ToString
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class EventIdAndParticipantId {
    private final int eventId;
    private final long countParticipant;
    private final boolean requestModeration;
}
