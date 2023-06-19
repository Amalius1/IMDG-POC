package pl.aml.bk.imdgpoc.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.aml.bk.imdgpoc.controller.model.InfoDto;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Service
@Profile("redis")
public class InfoRedisRepository implements InfoRepository {

    // todo - implement redis

    @Override
    public boolean infoByIdExists(UUID id) {
        return false;
    }

    @Override
    public InfoDto add(String info) {
        return null;
    }

    @Override
    public InfoDto updateInfo(InfoDto infoDto) {
        return null;
    }

    @Override
    public InfoDto findById(UUID id) {
        return null;
    }

    @Override
    public List<InfoDto> findAll() {
        return null;
    }

    @Override
    public BigInteger count() {
        return null;
    }
}
