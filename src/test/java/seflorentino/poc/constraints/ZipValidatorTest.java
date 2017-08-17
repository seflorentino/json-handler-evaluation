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

public class ZipValidatorTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void isValid() throws Exception {
        {
            UnderTest underTest = new UnderTest().setZip("12345");

            int errorCount = validator.validate(underTest).size();

            assertEquals(0, errorCount);
        }

        {
            UnderTest underTest = new UnderTest().setZip("12345-6789");

            int errorCount = validator.validate(underTest).size();

            assertEquals(0, errorCount);
        }
    }

    @Test
    public void invalidValues() {
        {
            UnderTest underTest = new UnderTest().setZip("123456");

            Map<String, ConstraintViolation<UnderTest>> violationMap = validator.validate(underTest).stream()
                    .collect(Collectors.toMap(ConstraintViolation::getMessageTemplate, Function.identity()));

            assertTrue(violationMap.containsKey("{seflorentino.poc.constraints.Zip.message}"));
        }

        {
            UnderTest underTest = new UnderTest().setZip("12345-");

            Map<String, ConstraintViolation<UnderTest>> violationMap = validator.validate(underTest).stream()
                    .collect(Collectors.toMap(ConstraintViolation::getMessageTemplate, Function.identity()));

            assertTrue(violationMap.containsKey("{seflorentino.poc.constraints.Zip.message}"));
        }

        {
            UnderTest underTest = new UnderTest().setZip("12345-67890");

            Map<String, ConstraintViolation<UnderTest>> violationMap = validator.validate(underTest).stream()
                    .collect(Collectors.toMap(ConstraintViolation::getMessageTemplate, Function.identity()));

            assertTrue(violationMap.containsKey("{seflorentino.poc.constraints.Zip.message}"));
        }

        {
            UnderTest underTest = new UnderTest().setZip("abcde");

            Map<String, ConstraintViolation<UnderTest>> violationMap = validator.validate(underTest).stream()
                    .collect(Collectors.toMap(ConstraintViolation::getMessageTemplate, Function.identity()));

            assertTrue(violationMap.containsKey("{seflorentino.poc.constraints.Zip.message}"));
        }

        {
            UnderTest underTest = new UnderTest().setZip("abcde-fghi");

            Map<String, ConstraintViolation<UnderTest>> violationMap = validator.validate(underTest).stream()
                    .collect(Collectors.toMap(ConstraintViolation::getMessageTemplate, Function.identity()));

            assertTrue(violationMap.containsKey("{seflorentino.poc.constraints.Zip.message}"));
        }
    }

    static class UnderTest {

        @Zip(required = true)
        private String zip;

        public String getZip() {
            return zip;
        }

        public UnderTest setZip(String zip) {
            this.zip = zip;
            return this;
        }
    }

}