package ru.practicum.users.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**Результат подтверждения/отклонения заявок на участие в событии*/
@Data
@RequiredArgsConstructor
public class EventRequestStatusUpdateResult {
    /**Подтвержденные заявки на участие в событии*/
    private final List<ParticipationRequestDto> confirmedRequests;
    /**Отклоненные заявки на участие в событии*/
    private final List<ParticipationRequestDto> rejectedRequests;

}
