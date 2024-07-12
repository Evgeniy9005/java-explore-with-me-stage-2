package ru.practicum.events.model;

import lombok.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.events.constants.State;
import ru.practicum.users.dto.UserShortDto;
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
    private Integer id;

    /**Краткое описание*/
    @Column(name = "annotation")
    private String annotation;

    /**Объект типа Category*/
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    /**Количество одобренных заявок на участие в данном событии*/
    @Column(name = "confirmed_requests")
    private Integer confirmedRequests;

    /**Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")*/
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    /**Полное описание события*/
    @Column(name = "description")
    private String description;

    /**Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")*/
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
    private Boolean paid;

    /**Ограничение на количество участников. Значение 0 - означает отсутствие ограничения*/
    @Column(name = "participant_limit")
    private Integer participantLimit;

    /**Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")*/
    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    /**Нужна ли пре-модерация заявок на участие*/
    @Column(name = "request_moderation")
    private Boolean requestModeration;

    /**Список состояний жизненного цикла события "enum": ["PENDING", "PUBLISHED", "CANCELED"]*/
    @Enumerated(EnumType.STRING)
    private State state;

    /**Заголовок события*/
    @Column(name = "title")
    private String title;

    /**Количество просмотров события*/
    @Column(name = "views")
    private Integer views;
}
