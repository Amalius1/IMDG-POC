package pl.aml.bk.imdgpoc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.aml.bk.imdgpoc.controller.model.InfoDto;
import pl.aml.bk.imdgpoc.repository.InfoRepository;

import java.math.BigInteger;
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
        return infoIMDGRepository.delete(id);
    }

    public BigInteger count() {
        return infoIMDGRepository.count();
    }

    public List<InfoDto> getPage(Integer size, Integer number) {
        return infoIMDGRepository.findPage(size, number);
    }
}
