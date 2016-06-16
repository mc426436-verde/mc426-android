package br.unicamp.ic.timeverde.dino.model.enums;

import br.unicamp.ic.timeverde.dino.R;

/**
 * The RoomTypeEnum enumeration.
 */
public enum RoomTypeEnum {
    BEDROOM(R.mipmap.garden_backgoud_card),
    GARDEN(R.color.colorPrimaryLight),
    KITCHEN(R.color.colorPrimaryLight),
    LIVINGROOM(R.color.colorPrimaryLight);

    private Integer roomImage;

    RoomTypeEnum(Integer roomImage) {
        this.roomImage = roomImage;
    }

    public Integer getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(Integer roomImage) {
        this.roomImage = roomImage;
    }
}
