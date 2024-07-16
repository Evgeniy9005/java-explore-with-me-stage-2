package ru.practicum;

public class ConflictException extends FormatMassage {

    public ConflictException(String massage) {
        super(massage);
    }

    public ConflictException(String massage, Object param) {
        super(massage, param);
    }

    public ConflictException(String massage, Object param1, Object param2) {
        super(massage, param1, param2);
    }

    public ConflictException(String massage, Object param1, Object param2, Object param3) {
        super(massage, param1, param2, param3);
    }
}
