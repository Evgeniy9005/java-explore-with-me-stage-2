package ru.practicum.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = IdsValidateValidator.class)
@Documented
public @interface IdsValidate {
    String message() default "{Один из идентификаторов равен 0!}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
