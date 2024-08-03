package ru.practicum.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class IdsValidateValidator implements ConstraintValidator<IdsValidate, List<Integer>> {

    @Override
    public boolean isValid(List<Integer> value, ConstraintValidatorContext constraintValidatorContext) {

        if (value != null) {
            return !value.stream().filter(integer -> integer == 0).findFirst().isPresent();
        }
        return true;
    }
}
