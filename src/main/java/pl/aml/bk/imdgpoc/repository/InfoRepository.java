package pl.aml.bk.imdgpoc.repository;

import pl.aml.bk.imdgpoc.controller.model.InfoDto;

import java.util.List;
import java.util.UUID;

public interface InfoRepository {

    boolean infoByIdExists(UUID id);

    InfoDto add(String info);

    InfoDto updateInfo(InfoDto infoDto);

    InfoDto findById(UUID id);

    List<InfoDto> findAll();

}
