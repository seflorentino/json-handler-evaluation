package seflorentino.poc.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Role {

    @Size(max = 250)
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }
}
