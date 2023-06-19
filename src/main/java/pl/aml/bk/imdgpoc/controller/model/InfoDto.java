package pl.aml.bk.imdgpoc.controller.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


public record InfoDto(UUID id, String info, String who) implements Serializable {

    public InfoDto(String info, String who) {
        this(UUID.randomUUID(), info, who);
    }

    public InfoDto(InfoDto infoDto, String who) {
        this(infoDto.id, infoDto.info(), who);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoDto infoDto = (InfoDto) o;
        return Objects.equals(id, infoDto.id) && Objects.equals(info, infoDto.info) && Objects.equals(who, infoDto.who);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, info, who);
    }
}
