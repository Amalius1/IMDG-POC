package pl.aml.bk.imdgpoc.controller.model;

import java.io.Serializable;
import java.util.UUID;


public record InfoDto(UUID id, String info, String who) implements Serializable {

    public InfoDto(String info, String who) {
        this(UUID.randomUUID(), info, who);
    }

    public InfoDto(InfoDto infoDto, String who) {
        this(infoDto.id, infoDto.info(), who);
    }

}
