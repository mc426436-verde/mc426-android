package br.unicamp.ic.timeverde.dino.model;

import java.io.Serializable;

public class Action implements Serializable{

    public static final String STATUS_ON = "ON";
    public static final String STATUS_OFF = "OFF";

    private Long id;
    private String status;
    private Device device;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(final Device device) {
        this.device = device;
    }
}
