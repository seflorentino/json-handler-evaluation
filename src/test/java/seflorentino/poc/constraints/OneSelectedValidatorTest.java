package seflorentino.poc.constraints;

import org.junit.Test;
import seflorentino.poc.domain.AbstractRadioSelect;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OneSelectedValidatorTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void isValid() throws Exception {
        UnderTest underTest = new UnderTest();

        Selectable selected = new Selectable();
        selected.setSelected(true);
        underTest.getSelectables().add(selected);

        Selectable notSelected = new Selectable();
        underTest.getSelectables().add(notSelected);

        int errorCount = validator.validate(underTest).size();

        assertEquals(0, errorCount);
    }

    @Test
    public void isValidWithInvalidValues() throws Exception {
        // Two Items selected
        {
            UnderTest underTest = new UnderTest();

            Selectable selected = new Selectable();
            selected.setSelected(true);
            underTest.getSelectables().add(selected);

            Selectable notSelected = new Selectable();
            notSelected.setSelected(true);
            underTest.getSelectables().add(notSelected);

            Map<String, ConstraintViolation<UnderTest>> violationMap = validator.validate(underTest).stream()
                    .collect(Collectors.toMap(ConstraintViolation::getMessageTemplate, Function.identity()));

            assertTrue(violationMap.containsKey("{seflorentino.poc.constraints.OneSelected.message}"));
        }

        // None selected
        {
            UnderTest underTest = new UnderTest();

            Selectable selected = new Selectable();
            underTest.getSelectables().add(selected);

            Selectable notSelected = new Selectable();
            underTest.getSelectables().add(notSelected);

            Map<String, ConstraintViolation<UnderTest>> violationMap = validator.validate(underTest).stream()
                    .collect(Collectors.toMap(ConstraintViolation::getMessageTemplate, Function.identity()));

            assertTrue(violationMap.containsKey("{seflorentino.poc.constraints.OneSelected.message}"));
        }
    }

    static class Selectable extends AbstractRadioSelect {

        private final Date timeStamp;

        Selectable() {
            timeStamp = new Date();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            Selectable that = (Selectable) o;

            return timeStamp != null ? timeStamp.equals(that.timeStamp) : that.timeStamp == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (timeStamp != null ? timeStamp.hashCode() : 0);
            return result;
        }
    }

    static class UnderTest {

        @OneSelected
        private Set<Selectable> selectables;

        public Set<Selectable> getSelectables() {
            if (selectables == null) {
                this.selectables = new HashSet<>();
            }
            return selectables;
        }

        public UnderTest setSelectables(Set<Selectable> selectables) {
            this.selectables = selectables;
            return this;
        }
    }

}