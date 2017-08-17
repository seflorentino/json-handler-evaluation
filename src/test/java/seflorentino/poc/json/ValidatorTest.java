package seflorentino.poc.json;

import org.junit.Test;
import seflorentino.poc.domain.Permission;
import seflorentino.poc.domain.Role;
import seflorentino.poc.domain.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class ValidatorTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void sizeTest() throws Exception {

        final String loooongString = "Loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong";

        User user = new User()
                .setId(100L)
                .setFirstName(loooongString)
                .setLastName(loooongString)
                .setEmail(loooongString)
                .setLocale(Locale.ENGLISH)
                .setPassword(loooongString)
                .addPermission(new Permission().setName("Create"))
                .addPermission(new Permission().setName("Write"))
                .addPermission(new Permission().setName("Delete"))
                .addRole(new Role().setName("RegularUser"));

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        Map<String, List<ConstraintViolation<User>>> errorsByPath = violations.stream()
                .collect(Collectors.groupingBy(v -> v.getPropertyPath().toString()));

        assertTrue(errorsByPath.containsKey("firstName"));
        assertTrue(errorsByPath.containsKey("lastName"));
        assertTrue(errorsByPath.containsKey("email"));
        assertTrue(errorsByPath.containsKey("password"));
    }

}
