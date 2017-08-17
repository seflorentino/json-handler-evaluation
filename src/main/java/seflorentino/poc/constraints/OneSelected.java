package seflorentino.poc.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = OneSelectedValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface OneSelected {
    String message() default "{seflorentino.poc.constraints.OneSelected.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String label() default "item";
}
