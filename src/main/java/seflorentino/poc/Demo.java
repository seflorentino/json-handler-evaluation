package seflorentino.poc;

import seflorentino.poc.domain.User;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Demo {

    private static final String DEMO1 = "{}";

    public static void main(String[] args) throws IOException {

        JsonSerializer<User> serializer = new JsonSerializer<>(User.class);

        List<ConstraintViolation> violations = new ArrayList<>();
        User user = serializer.fromJson(DEMO1, violations);

        System.out.println("Valid: " + (violations.size() == 0));
        violations.stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);


        JsonSerializer<User> esSerializer = JsonSerializer.create(User.class, Locale.forLanguageTag("es"));

        List<ConstraintViolation> esViolations = new ArrayList<>();
        esSerializer.fromJson(DEMO1, esViolations);
        System.out.println("Valid: " + (esViolations.size() == 0));
        esViolations.stream()
                .map(ConstraintViolation::getMessage)
                .forEach(System.out::println);
    }
}
