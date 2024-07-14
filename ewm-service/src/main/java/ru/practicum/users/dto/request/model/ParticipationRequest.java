package ru.practicum.users.dto.request.model;


import lombok.*;
import ru.practicum.constants.StatusRequest;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "users", schema = "public")
public class ParticipationRequest {
    /**Идентификатор заявки*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**Дата и время создания заявки. example: 2022-09-06T21:10:05.432*/
    @Column(name = "created")
    private String created;
    /**Идентификатор события*/

    private int event;
    /**Идентификатор пользователя, отправившего заявку*/
    private int requester;
    /**Статус заявки*/
    @Enumerated(EnumType.STRING)
    private StatusRequest status;
}
