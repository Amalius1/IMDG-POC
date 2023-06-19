package pl.aml.bk.hazelcastpoc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.aml.bk.hazelcastpoc.controller.model.InfoDto;
import pl.aml.bk.hazelcastpoc.repository.InfoRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InfoService {

    private final InfoRepository infoIMDGRepository;

    public List<InfoDto> getInfoList() {
        return infoIMDGRepository.findAll();
    }

    public InfoDto getInfo(UUID id) {
        return infoIMDGRepository.findById(id);
    }

    public InfoDto addNewInfo(String info) {
        return infoIMDGRepository.add(info);
    }

    public InfoDto updateInfo(InfoDto infoDto) {
        boolean infoByIdExists = infoIMDGRepository.infoByIdExists(infoDto.id());
        if (infoByIdExists) {
            return infoIMDGRepository.updateInfo(infoDto);
        }
        return infoIMDGRepository.add(infoDto.info());
    }

    public boolean delete(UUID id) {
        return false;
    }
}
