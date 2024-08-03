package ru.practicum.annotations;

import ru.practicum.util.Util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class DoDateArrivedValidator implements ConstraintValidator<DoDateArrived, String>{


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value != null) {
                //Изменение даты события на уже наступившую
            return LocalDateTime.now().isBefore(Util.getDate(value));
        }
        return true;
    }
}
