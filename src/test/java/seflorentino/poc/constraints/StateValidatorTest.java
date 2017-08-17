package seflorentino.poc.constraints;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StateValidatorTest {

    public static final String MESSAGE = "{seflorentino.poc.constraints.State.message}";

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void isValid() throws Exception {
        {
            UnderTest underTest = new UnderTest().setState("mn");

            int errorCount = validator.validate(underTest).size();

            assertEquals(0, errorCount);
        }

        {
            UnderTest underTest = new UnderTest().setState("wa");

            int errorCount = validator.validate(underTest).size();

            assertEquals(0, errorCount);
        }
    }

    @Test
    public void isValidWithInvalidValues() throws Exception {
        {
            UnderTest underTest = new UnderTest().setState("wisc");
            Map<String, ConstraintViolation<UnderTest>> violationMap = validator.validate(underTest).stream()
                    .collect(Collectors.toMap(ConstraintViolation::getMessageTemplate, Function.identity()));
            assertTrue(violationMap.containsKey(MESSAGE));
        }

        {
            UnderTest underTest = new UnderTest().setState("Minnesota");
            Map<String, ConstraintViolation<UnderTest>> violationMap = validator.validate(underTest).stream()
                    .collect(Collectors.toMap(ConstraintViolation::getMessageTemplate, Function.identity()));
            assertTrue(violationMap.containsKey(MESSAGE));
        }

        {
            UnderTest underTest = new UnderTest().setState("m");
            Map<String, ConstraintViolation<UnderTest>> violationMap = validator.validate(underTest).stream()
                    .collect(Collectors.toMap(ConstraintViolation::getMessageTemplate, Function.identity()));
            assertTrue(violationMap.containsKey(MESSAGE));
        }
    }

    static class UnderTest {

        @State(required = true)
        private String state;

        public String getState() {
            return state;
        }

        public UnderTest setState(String state) {
            this.state = state;
            return this;
        }
    }

}