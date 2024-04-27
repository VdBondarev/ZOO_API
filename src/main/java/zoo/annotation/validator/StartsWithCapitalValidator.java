package zoo.annotation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import zoo.annotation.StartsWithCapital;

public class StartsWithCapitalValidator implements ConstraintValidator<StartsWithCapital, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || Character.isUpperCase(value.charAt(0));
    }
}
