package ru.practicum.users.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.users.request.dto.ParticipationRequestDto;

import java.util.List;

/**Результат подтверждения/отклонения заявок на участие в событии*/
@Data
@RequiredArgsConstructor
public class EventRequestStatusUpdateResult {
    /**Подтвержденные заявки на участие в событии*/
    private final List<ParticipationRequestDto> confirmedRequests;
    /**Отклоненные заявки на участие в событии*/
    private final List<ParticipationRequestDto> rejectedRequests;

    public void addConfirmedRequests(ParticipationRequestDto prDto) {
        confirmedRequests.add(prDto);
    }

    public void addRejectedRequests(ParticipationRequestDto prDto) {
        rejectedRequests.add(prDto);
    }
}
