package pl.aml.bk.hazelcastpoc.repository;

import pl.aml.bk.hazelcastpoc.controller.model.InfoDto;

import java.util.List;
import java.util.UUID;

public interface InfoRepository {

    boolean infoByIdExists(UUID id);

    InfoDto add(String info);

    InfoDto updateInfo(InfoDto infoDto);

    InfoDto findById(UUID id);

    List<InfoDto> findAll();

}
