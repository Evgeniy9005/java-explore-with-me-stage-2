package ru.practicum.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.BadRequestException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Util {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Pageable page(int from, int size) {
        validFromSize(from,size);
        PageRequest pageRequest;
        if (from > 0) {
            pageRequest = PageRequest.of(from / size,size);
            log.info("{}",pageRequest);
            return pageRequest;
        }

        pageRequest = PageRequest.of(from,size);//по умолчанию
        log.info("{}",pageRequest);
        return PageRequest.of(from,size);
    }


    public static Pageable page(int from, int size, Sort sort) {

        validFromSize(from,size);
        PageRequest pageRequest;
        if (from > 0) {
            pageRequest = PageRequest.of(from / size,size,sort);
            log.info("{}",pageRequest);
            return pageRequest;
        }
        pageRequest = PageRequest.of(from,size,sort); //по умолчанию
        log.info("{}",pageRequest);
        return pageRequest;
    }

    public static int start(int from, int size) {
        validFromSize(from,size);

        if (from > 0) {
            log.info("start {}",from  % size);
            return from  % size;
        }

        return 0;
    }

    private static void validFromSize(int from, int size) {
        if (from < 0 || size < 1) {
            throw new BadRequestException(
                    "Не верно заданы входные параметры для отображения данных в диапазоне от # до #  ", from, size);
        }
    }

    public static <T> List<T> getElementsFrom(List<T> list, int start) {

        return list.stream().skip(start).collect(Collectors.toList());
    }

    public static List<Integer> getNumbers(String arrayString) {
        if(arrayString == null) {
            throw new BadRequestException(
                    "Не верно заданы входные параметры #, " +
                            "Массив данных должен быть числа и разделены запятыми, без пробелов",arrayString);
        }

        String [] result = arrayString.split(",");


        List<Integer> list = Arrays.stream(result).map(i-> {
            try {
                return Integer.parseInt(i);
            } catch (NumberFormatException e) {
                throw new BadRequestException(
                        "Не верно заданы входные параметры #, " +
                                "Массив данных должен быть числа и разделены запятыми, без пробелов",arrayString);
            }
        }).collect(Collectors.toList());
        log.info("Сформированный массив из строки - {}",list);
        return list;

    }

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }

    public static LocalDateTime getDate(String date) {
        return LocalDateTime.parse(date,formatter);
    }

    public static String encodeValue(String value) {
        String result = "";
        try {
            URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            log.debug("Ошибка расшифровки данных {}",value);
        }
        return result;
    }

}
