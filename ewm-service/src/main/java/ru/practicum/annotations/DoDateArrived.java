package ru.practicum.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = DoDateArrivedValidator.class)
@Documented
public @interface DoDateArrived {
    String message() default "{Изменение даты события на уже наступившую!}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
