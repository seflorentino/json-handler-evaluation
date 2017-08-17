package seflorentino.poc.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ZipValidator implements ConstraintValidator<Zip, String> {

    private static final Pattern zipPattern = Pattern.compile("^[0-9]{5}(?:-[0-9]{4})?$");

    private boolean required;

    @Override
    public void initialize(Zip constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (!required && value == null) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        if (!zipPattern.matcher(value).matches()) {
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();
            return false;
        }

        return true;
    }
}
