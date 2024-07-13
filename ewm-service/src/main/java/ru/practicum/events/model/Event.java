package ru.practicum.events.model;

import lombok.*;
import ru.practicum.category.model.Category;
import ru.practicum.events.constants.State;
import ru.practicum.users.model.User;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "events", schema = "public")
public class Event {
    /**Идентификатор события*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**Краткое описание*/
    @Column(name = "annotation")
    private String annotation;

    /**Объект типа Category*/
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    /**Количество одобренных заявок на участие в данном событии*/
    @Column(name = "confirmed_requests")
    private int confirmedRequests;

    /**Дата и время создания события*/
    @Column(name = "created_on")
    @Builder.Default
    private LocalDateTime createdOn = LocalDateTime.now();

    /**Полное описание события*/
    @Column(name = "description")
    private String description;

    /**Дата и время на которые намечено событие*/
    @Column(name = "event_date")
    private LocalDateTime eventDate;

    /**Объект типа User*/
    @ManyToOne
    @JoinColumn(name = "id_initiator")
    private User initiator;

    /**Широта*/
    @Column(name = "lat")
    private float lat;

    /**Долгота*/
    @Column(name = "lon")
    private float lon;

    /**Нужно ли оплачивать участие*/
    @Column(name = "paid")
    private boolean paid;

    /**Ограничение на количество участников. Значение 0 - означает отсутствие ограничения*/
    @Column(name = "participant_limit")
    private int participantLimit;

    /**Дата и время публикации события*/
    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    /**Нужна ли пре-модерация заявок на участие*/
    @Column(name = "request_moderation")
    private boolean requestModeration;

    /**Список состояний жизненного цикла события "enum": ["PENDING", "PUBLISHED", "CANCELED"]*/
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private State state = State.PENDING;

    /**Заголовок события*/
    @Column(name = "title")
    private String title;

    /**Количество просмотров события*/
    @Column(name = "views")
    private int views;
}
