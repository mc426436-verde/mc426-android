package br.unicamp.ic.timeverde.dino.model;

import java.io.Serializable;
import java.util.List;

public class Macro implements Serializable {

    private Long id;
    private String name;
    private String description;
    private List<Action> actions;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<Action> getAction() {
        return actions;
    }

    public void setAction(final List<Action> actions) {
        this.actions = actions;
    }
}
