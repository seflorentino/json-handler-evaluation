package seflorentino.poc.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import seflorentino.poc.domain.Permission;
import seflorentino.poc.domain.Role;
import seflorentino.poc.domain.User;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class MapperTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void serialize() throws Exception {

        User user = new User()
                .setId(100L)
                .setFirstName("Test")
                .setLastName("Guy")
                .setEmail("testguy@poc.com")
                .setLocale(Locale.ENGLISH)
                .setPassword("ILoveMyJob1")
                .addPermission(new Permission().setName("Create"))
                .addPermission(new Permission().setName("Write"))
                .addPermission(new Permission().setName("Delete"))
                .addRole(new Role().setName("RegularUser"));

        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);

        /*
         * Use javax.json to validate results
         */
        JsonReader reader = Json.createReader(new StringReader(jsonString));

        JsonObject jsonObject = reader.readObject();

        Long id = Long.valueOf(jsonObject.get("id").toString());
        assertEquals((Long) 100L, id);
        assertEquals("Test", jsonObject.getString("firstName"));
        assertEquals("Guy", jsonObject.getString("lastName"));
        assertEquals("testguy@poc.com", jsonObject.getString("email"));
        assertEquals("en", jsonObject.getString("locale"));

        assertNull(jsonObject.get("password"));

        Set<String> rolesNames = jsonObject.getJsonArray("roles").stream()
                .map(JsonValue::asJsonObject)
                .map(jo -> jo.getString("name"))
                .collect(Collectors.toSet());

        assertTrue(rolesNames.contains("RegularUser"));

        Set<String> permissionNames = jsonObject.getJsonArray("permissions").stream()
                .map(JsonValue::asJsonObject)
                .map(jo -> jo.getString("name"))
                .collect(Collectors.toSet());

        assertTrue(permissionNames.contains("Create"));
        assertTrue(permissionNames.contains("Write"));
        assertTrue(permissionNames.contains("Delete"));
    }

    @Test
    public void deserialize() throws Exception {

        final String json = "{" +
                "    \"id\" : 100," +
                "    \"firstName\" : \"Test\"," +
                "    \"lastName\" : \"Guy\"," +
                "    \"email\" : \"testguy@poc.com\"," +
                "    \"locale\" : \"en\"," +
                "    \"password\" : \"ILoveMyJob1\"," +
                "    \"roles\" : [{" +
                "          \"name\" : \"RegularUser\"" +
                "      }]," +
                "    \"permissions\" : [{" +
                "          \"name\" : \"Create\"" +
                "      }, {" +
                "          \"name\" : \"Write\"" +
                "      }, {" +
                "          \"name\" : \"Delete\"" +
                "      }]" +
                " }";

        User user = mapper.readValue(json, User.class);

        assertEquals((Long) 100L, user.getId());
        assertEquals("Test", user.getFirstName());
        assertEquals("Guy", user.getLastName());
        assertEquals("testguy@poc.com", user.getEmail());
        assertEquals(Locale.ENGLISH, user.getLocale());
        assertEquals("ILoveMyJob1", user.getPassword());

        final Set<String> requiredRoles = new HashSet<>();
        requiredRoles.add("RegularUser");

        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        assertTrue(roleNames.containsAll(requiredRoles));

        final Set<String> requiredPermissions = new HashSet<>();
        requiredPermissions.add("Create");
        requiredPermissions.add("Write");
        requiredPermissions.add("Delete");

        Set<String> permissionNames = user.getPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());
        assertTrue(permissionNames.containsAll(requiredPermissions));
    }

}
