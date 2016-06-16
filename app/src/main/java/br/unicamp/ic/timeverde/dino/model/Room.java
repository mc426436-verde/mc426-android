package br.unicamp.ic.timeverde.dino.model;

import java.io.Serializable;

import br.unicamp.ic.timeverde.dino.model.enums.RoomTypeEnum;


public class Room implements Serializable {

    private Long id;
    private String description;
    private String name;
    private RoomTypeEnum type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomTypeEnum getType() {
        return type;
    }

    public void setType(RoomTypeEnum type) {
        this.type = type;
    }
}
