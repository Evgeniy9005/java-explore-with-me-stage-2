package ru.practicum;

import ru.practicum.client.StatsClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Get {
    public static void main(String[] args) {


        StatsClient statsClient = new StatsClient("localhost","9090");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String data = LocalDateTime.now().format(formatter);
        System.out.println("*".repeat(100));
        System.out.println(statsClient.put("{\n" +
                "  \"app\": \"ewm-main-service\",\n" +
                "  \"uri\": \"/events/1\",\n" +
                "  \"ip\": \"192.163.0.1\",\n" +
                "  \"timestamp\": \""+ data +"\"\n" +
                "}"));
        data = LocalDateTime.now().plusSeconds(1).format(formatter);
        System.out.println(statsClient.put("{\n" +
                "  \"app\": \"ewm-main-service\",\n" +
                "  \"uri\": \"/events/1\",\n" +
                "  \"ip\": \"192.163.0.1\",\n" +
                "  \"timestamp\": \""+ data +"\"\n" +
                "}"));
        data = LocalDateTime.now().plusSeconds(2).format(formatter);
        System.out.println(statsClient.put("{\n" +
                "  \"app\": \"ewm-main-service\",\n" +
                "  \"uri\": \"/events/1\",\n" +
                "  \"ip\": \"192.163.0.2\",\n" +
                "  \"timestamp\": \""+ data +"\"\n" +
                "}"));
        data = LocalDateTime.now().plusSeconds(3).format(formatter);
        System.out.println(statsClient.put("{\n" +
                "  \"app\": \"ewm-main-service\",\n" +
                "  \"uri\": \"/events/1\",\n" +
                "  \"ip\": \"192.163.0.2\",\n" +
                "  \"timestamp\": \""+ data +"\"\n" +
                "}"));
        data = LocalDateTime.now().plusSeconds(4).format(formatter);
        System.out.println(statsClient.put("{\n" +
                "  \"app\": \"ewm-main-service\",\n" +
                "  \"uri\": \"/events/2\",\n" +
                "  \"ip\": \"192.163.0.1\",\n" +
                "  \"timestamp\": \""+ data +"\"\n" +
                "}"));
        data = LocalDateTime.now().plusSeconds(5).format(formatter);
        System.out.println(statsClient.put("{\n" +
                "  \"app\": \"ewm-main-service\",\n" +
                "  \"uri\": \"/events/2\",\n" +
                "  \"ip\": \"192.163.0.1\",\n" +
                "  \"timestamp\": \""+ data +"\"\n" +
                "}"));
        data = LocalDateTime.now().plusSeconds(5).format(formatter);
        System.out.println(statsClient.put("{\n" +
                "  \"app\": \"ewm-main-service\",\n" +
                "  \"uri\": \"/events/3\",\n" +
                "  \"ip\": \"192.163.0.3\",\n" +
                "  \"timestamp\": \""+ data +"\"\n" +
                "}"));
        System.out.println("*".repeat(100));

        LocalDateTime start = LocalDateTime.now().minusMonths(1);
        LocalDateTime end = LocalDateTime.now().plusMonths(1);

        System.out.println("+".repeat(100));
        System.out.println(statsClient.get(start,end));
        System.out.println("+".repeat(100));

        System.out.println("&".repeat(100));
        System.out.println(statsClient.get(start,end,false));
        System.out.println("&".repeat(100));
    }

}
