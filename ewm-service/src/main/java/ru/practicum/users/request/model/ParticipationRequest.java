package ru.practicum.users.request.model;


import lombok.*;
import ru.practicum.constants.StatusRequest;
import ru.practicum.events.model.Event;
import ru.practicum.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "participation_request", schema = "public")
public class ParticipationRequest {
    /**Идентификатор заявки*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**Дата и время создания заявки. example: 2022-09-06T21:10:05.432*/
    @Column(name = "created")
    private LocalDateTime created;
    /**Событие*/
    @OneToOne
    @JoinColumn(name = "id_event")
    private Event event;
    /**Идентификатор пользователя, отправившего заявку*/
    @OneToOne
    @JoinColumn(name = "id_requester")
    private User requester;
    /**Статус заявки*/
    @Enumerated(EnumType.STRING)
    private StatusRequest status;
}
