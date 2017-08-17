package seflorentino.poc.domain;

import javax.validation.constraints.Size;

public class Permission {

    @Size(max = 250)
    private String name;

    public String getName() {
        return name;
    }

    public Permission setName(String name) {
        this.name = name;
        return this;
    }
}
