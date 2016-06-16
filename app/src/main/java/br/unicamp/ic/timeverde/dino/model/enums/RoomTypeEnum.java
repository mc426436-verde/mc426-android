package br.unicamp.ic.timeverde.dino.model.enums;

import br.unicamp.ic.timeverde.dino.R;

/**
 * The RoomTypeEnum enumeration.
 */
public enum RoomTypeEnum {
    BEDROOM(R.mipmap.bedroom_backgoud_card),
    GARDEN(R.mipmap.garden_background_card),
    KITCHEN(R.color.colorPrimaryLight),
    LIVINGROOM(R.mipmap.living_room_background_card);

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
