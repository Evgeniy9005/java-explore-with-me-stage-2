package ru.practicum.events.model;

import lombok.*;
import ru.practicum.category.model.Category;
import ru.practicum.constants.State;
import ru.practicum.users.model.User;
import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

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

    /**Нужна ли пре-модерация заявок на участие. Если true,
     то все заявки будут ожидать подтверждения инициатором события.
     Если false - то будут подтверждаться автоматически*/
    @Builder.Default
    @Column(name = "request_moderation")
    private boolean requestModeration = true;

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

    /**Подборка*/
    @Column(name = "id_compilation")
    private int compilation;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id && confirmedRequests == event.confirmedRequests && Float.compare(event.lat, lat) == 0 && Float.compare(event.lon, lon) == 0 && paid == event.paid && participantLimit == event.participantLimit && requestModeration == event.requestModeration && views == event.views && compilation == event.compilation && Objects.equals(annotation, event.annotation) && Objects.equals(category, event.category) && Objects.equals(createdOn, event.createdOn) && Objects.equals(description, event.description) && Objects.equals(eventDate, event.eventDate) && Objects.equals(initiator, event.initiator) && Objects.equals(publishedOn, event.publishedOn) && state == event.state && Objects.equals(title, event.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, annotation, category, confirmedRequests, createdOn, description, eventDate, initiator, lat, lon, paid, participantLimit, publishedOn, requestModeration, state, title, views, compilation);
    }
}
