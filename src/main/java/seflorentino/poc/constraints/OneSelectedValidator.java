package seflorentino.poc.constraints;

import seflorentino.poc.domain.AbstractRadioSelect;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class OneSelectedValidator implements ConstraintValidator<OneSelected, Collection<? extends AbstractRadioSelect>> {

    @Override
    public void initialize(OneSelected constraintAnnotation) {
    }

    @Override
    public boolean isValid(Collection<? extends AbstractRadioSelect> radioSelects, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        if (radioSelects == null || radioSelects.isEmpty()) {
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();
            return false;
        }

        long selectedItems = radioSelects.stream()
                .filter(AbstractRadioSelect::isSelected)
                .count();

        if (selectedItems != 1) {
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();
            return false;
        }

        return true;
    }
}
