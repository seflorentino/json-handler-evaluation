package seflorentino.poc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import seflorentino.poc.constraints.Locale;
import seflorentino.poc.constraints.OneSelected;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


public class User {

    private Long id;

    @NotEmpty(message = "{User.firstName.required}")
    @Size(max = 250)
    private String firstName;

    @NotEmpty(message = "{User.lastName.required}")
    @Size(max = 250)
    private String lastName;

    @NotEmpty(message = "{User.email.required}")
    @Email
    @Size(max = 250)
    private String email;

    @NotEmpty(message = "{User.password.required}")
    @Size(max = 250)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Locale
    private java.util.Locale locale;

    private Set<Role> roles;

    private Set<Permission> permissions;

    @OneSelected(label = "address")
    private Set<Address> addresses;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public java.util.Locale getLocale() {
        return locale;
    }

    public User setLocale(java.util.Locale locale) {
        this.locale = locale;
        return this;
    }

    public Set<Role> getRoles() {
        if (roles == null) {
            this.roles = new HashSet<>();
        }
        return roles;
    }

    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Set<Permission> getPermissions() {
        if (permissions == null) {
            this.permissions = new HashSet<>();
        }
        return permissions;
    }

    public User setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }

    public User addRole(Role role) {
        getRoles().add(role);
        return this;
    }

    public User addPermission(Permission permission) {
        getPermissions().add(permission);
        return this;
    }

    public Set<Address> getAddresses() {
        if (addresses == null) {
            this.addresses = new HashSet<>();
        }
        return addresses;
    }

    public User setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }
}
