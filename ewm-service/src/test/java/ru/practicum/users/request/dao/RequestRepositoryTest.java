package ru.practicum.users.request.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.admin.AdminService;
import ru.practicum.category.dao.CategoryRepository;
import ru.practicum.category.model.Category;
import ru.practicum.constants.StatusRequest;
import ru.practicum.data.Data.*;
import ru.practicum.events.dao.EventsRepository;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.model.Event;
import ru.practicum.users.UserService;
import ru.practicum.users.dao.UserRepository;
import ru.practicum.users.model.User;
import ru.practicum.users.request.NewUserRequest;
import ru.practicum.users.request.model.EventIdAndParticipantId;
import ru.practicum.users.request.model.ParticipationRequest;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practicum.data.Data.generationData;
import static ru.practicum.data.Data.printList;


@DataJpaTest
class RequestRepositoryTest {

    /*@Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;*/

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private EntityManager entityManager;

    private static List<Event> eventList;

    private static List<User> userList;

    private static List<Category> categoryList;


    private List<Event> saveEventList;

    private List<User> saveUserList;

    private List<Category> saveCategoryList;

    private List<ParticipationRequest> savePrList;

    private ParticipationRequest pr1;
    private ParticipationRequest pr2;
    private ParticipationRequest pr3;

    @BeforeAll
    static void data() {
        userList = generationData(3,User.class);
        categoryList = generationData(1,Category.class);
        printList(userList,"uuu");
        printList(categoryList,"ccc");
        //eventList = Data.generationData(3,Event.class, , );
    }

    @BeforeEach
    void start() {
        saveUserList = userRepository.saveAll(userList);
        printList(saveUserList,"<u>");
        saveCategoryList = categoryRepository.saveAll(categoryList);
        printList(saveCategoryList,"<c>");
        eventList = generationData(3,Event.class,saveUserList.get(0),saveCategoryList.get(0));
        printList(eventList,"eee");

        saveEventList = eventsRepository.saveAll(eventList);
        printList(saveEventList,"<e>");

        pr1 = requestRepository.save(
                ((ParticipationRequest) generationData(1,
                        ParticipationRequest.class,
                        saveEventList.get(0),
                        saveUserList.get(0)
                ).get(0)).toBuilder().status(StatusRequest.CONFIRMED).build());

        pr2 = requestRepository.save(
                ((ParticipationRequest) generationData(1,
                        ParticipationRequest.class,
                        saveEventList.get(0),
                        saveUserList.get(1)
                ).get(0)).toBuilder().status(StatusRequest.CONFIRMED).build());

        pr3 = requestRepository.save(
                ((ParticipationRequest) generationData(1,
                        ParticipationRequest.class,
                        saveEventList.get(1),
                        saveUserList.get(0)
                ).get(0)).toBuilder().status(StatusRequest.CONFIRMED).build());

    }

    @AfterEach
    void end() {
        entityManager.createNativeQuery("ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE EVENTS ALTER COLUMN ID RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE PARTICIPATION_REQUEST ALTER COLUMN ID RESTART WITH 1").executeUpdate();
    }

    @Test
    void findByRequesterId() {
    }

    @Test
    void findByEventInitiatorIdAndEventId() {
    }

    @Test
    void numberParticipants() {
    }

    @Test
    void numberEventsAndNumberParticipants() {

        assertEquals(pr1.getEvent(),saveEventList.get(0));
        assertEquals(pr2.getEvent(),saveEventList.get(0));
        assertEquals(pr1.getRequester(),saveUserList.get(0));

        EventIdAndParticipantId ep = requestRepository
                .numberEventsAndNumberParticipants(1, StatusRequest.CONFIRMED);
        System.out.println(ep);
        assertNotNull(ep);
        assertEquals(ep.getEventId(),1);
        assertEquals(ep.getCountParticipant(),2);
    }
}