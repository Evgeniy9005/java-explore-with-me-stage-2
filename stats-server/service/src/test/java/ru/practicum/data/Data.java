package ru.practicum.data;

import dto.ViewStats;
import ru.practicum.exceptions.model.EndpointHit;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Data {

    public static final LocalDateTime CREATED_DATE = LocalDateTime.of(2024,1,1,1,1);
    public static final LocalDateTime START_DATE = LocalDateTime.of(2024,2,2,2,2);
    public static final LocalDateTime END_DATE = LocalDateTime.of(2024,3,3,3,3);

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     <p><b>- EndpointHit</b> баз параметров objects;</p>
     <p><b>- ViewStats</b> баз параметров objects;</p>
     */
    public static <T> List<T> generationData(Integer createObjects, Type t, Object... objects) {

        return (List<T>) IntStream.iterate(1,i -> i + 1)
                        .mapToObj(i -> getData(i, t, objects))
                        .limit(createObjects)
                        .collect(Collectors.toList());
    }

    private static <D> D getData(int i, Type type, Object... objects) {

        if (type.equals(ViewStats.class)) {
            return (D) new ViewStats("app","uri " + i, Long.valueOf(i));
        }

        if (type.equals(EndpointHit.class)) {
            return (D) EndpointHit.builder()
                    .id(i)
                    .app("app" + i)
                    .uri("/uri" + i)
                    .ip("192.168.0." + i)
                    .timestamp(LocalDateTime.now())
                    .build();
        }
/*
        if (type.equals(Item.class)) {
            if (objects.length == 2) {
                if (objects[0].getClass().equals(User.class) && objects[1].getClass().equals(Long.class)) {

                    return (D) new Item(i, "item" + i, "описание вещи " + i, true, (User) objects[0],(long)objects[1]);
                }
            }
        }


        if (type.equals(Booking.class)) {
            if (objects.length == 2) {
                if (objects[0].getClass().equals(User.class) && objects[1].getClass().equals(Item.class)) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                    return (D) Booking.builder()
                            .id(i)
                            .booker((User) objects[0])
                            .item((Item) objects[1])
                            .start(LocalDateTime.now())
                            .end(LocalDateTime.now().plusDays(1))
                            .status(Status.APPROVED)
                            .build();
                }
            }

        }

        if (type.equals(CreateBooking.class)) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            if (objects.length == 1) {
                if (objects[0].getClass().equals(Long.class)) {
                    return (D) new CreateBooking((long) objects[0],
                            LocalDateTime.now().plusSeconds(60),
                            LocalDateTime.now().plusDays(1));
                }
            }
        }

        if (type.equals(Comment.class)) {
            if (objects.length == 2) {
                if (objects[0].getClass().equals(Item.class) && objects[1].getClass().equals(User.class)) {
                    return (D) Comment.builder()
                            .id(i)
                            .text("Text" + i)
                            .item((Item) objects[0])
                            .author((User) objects[1])
                            .created(LocalDateTime.of(2024,1,1,1,1,1))
                            .build();
                }
            }
        }*/

        return null;
    }

    public static <T> void printList(Collection<T> list, String separator) {
        System.out.println(separator.repeat(33));
        list.stream().forEach(System.out::println);
        System.out.println(separator.repeat(33));
    }

    public static <T> void printList(Collection<T> list) {
        System.out.println("~*~".repeat(33));
        list.stream().forEach(System.out::println);
        System.out.println("~*~".repeat(33));
    }

    /**
     <p><b>- Формат </b> "yyyy-MM-dd HH:mm:ss"</p>
     */
    public static DateTimeFormatter getFormatter() {
        return formatter;
    }

    public static DateTimeFormatter getMyFormatter(String formatter) {
        return DateTimeFormatter.ofPattern(formatter);
    }

    public static void separato__________________________________r() {
        System.out.println("***************************************************************");
    }

}
