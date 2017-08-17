package seflorentino.poc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class JsonSerializer<T> {

    private final ObjectMapper objectMapper;
    private final Validator validator;
    private final Class<T> type;

    public JsonSerializer(Class<T> type) {
        this.type = type;
        this.objectMapper = new ObjectMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public JsonSerializer(Class<T> type, Validator validator) {
        this.type = type;
        this.objectMapper = new ObjectMapper();
        this.validator = validator;
    }

    public T fromJson(String json, List<ConstraintViolation> violations) throws IOException {
        T t = objectMapper.readValue(json, type);

        violations.addAll(validator.validate(t));

        return t;
    }

    public String toJson(T t) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(t);
    }

    public static <T> JsonSerializer<T> create(Class<T> type, Locale locale) {
        LocaleAwareMessageInterpolator interpolator = new LocaleAwareMessageInterpolator();
        interpolator.setDefaultLocale(locale);

        Validator validator = Validation.byDefaultProvider().configure()
                .messageInterpolator(interpolator)
                .buildValidatorFactory()
                .getValidator();

        return new JsonSerializer<>(type, validator);
    }

    public static class LocaleAwareMessageInterpolator extends
            ResourceBundleMessageInterpolator {

        private Locale defaultLocale = Locale.getDefault();

        public void setDefaultLocale(Locale defaultLocale) {
            this.defaultLocale = defaultLocale;
        }

        @Override
        public String interpolate(final String messageTemplate,
                                  final Context context) {
            return interpolate(messageTemplate, context, defaultLocale);
        }

        @Override
        public String interpolate(final String messageTemplate,
                                  final Context context, final Locale locale) {
            return super.interpolate(messageTemplate, context, locale);
        }
    }

}
