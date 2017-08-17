package seflorentino.poc.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LocaleValidator implements ConstraintValidator<Locale, java.util.Locale> {

    @Override
    public void initialize(Locale constraintAnnotation) {
    }

    @Override
    public boolean isValid(java.util.Locale value, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        for (java.util.Locale locale : java.util.Locale.getAvailableLocales()) {
            if (locale.equals(value)) {
                return true;
            }
        }

        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();

        return false;
    }
}
