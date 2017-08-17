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

public class LocaleValidatorTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void isValid() throws Exception {
        UnderTest testObject = new UnderTest().setLocale(new java.util.Locale("en"));


        int errorCount = validator.validate(testObject).size();

        assertEquals(0, errorCount);
    }

    @Test
    public void testInvalidLocale() throws Exception {

        UnderTest testObject = new UnderTest()
                .setLocale(new java.util.Locale("bs"));


        Map<String, ConstraintViolation<UnderTest>> violationMap = validator.validate(testObject).stream()
                .collect(Collectors.toMap(ConstraintViolation::getMessageTemplate, Function.identity()));

        assertTrue(violationMap.containsKey("{seflorentino.poc.constraints.Locale.message}"));
    }


    public static class UnderTest {

        @Locale
        private java.util.Locale locale;

        public java.util.Locale getLocale() {
            return locale;
        }

        public UnderTest setLocale(java.util.Locale locale) {
            this.locale = locale;
            return this;
        }
    }

}