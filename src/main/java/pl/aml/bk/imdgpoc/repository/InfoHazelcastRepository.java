package pl.aml.bk.imdgpoc.repository;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pl.aml.bk.imdgpoc.cache.hazelcast.HazelcastStatics;
import pl.aml.bk.imdgpoc.common.logging.LogExecutionTime;
import pl.aml.bk.imdgpoc.controller.model.InfoDto;

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("hazelcast")
public class InfoHazelcastRepository implements InfoRepository {

    private final HazelcastInstance hazelcastInstance;
    private final String HOST_NAME;

    @SneakyThrows
    public InfoHazelcastRepository(@Qualifier("mainInstance") HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
        this.HOST_NAME = InetAddress.getLocalHost().getHostName();
    }

    @Override
    @LogExecutionTime
    public boolean infoByIdExists(UUID id) {
        IMap<UUID, InfoDto> infoCache = hazelcastInstance.getMap(HazelcastStatics.INFO_CACHE);
        return infoCache.get(id) != null;
    }

    @Override
    @LogExecutionTime
    public InfoDto add(String info) {
        IMap<UUID, InfoDto> infoCache = hazelcastInstance.getMap(HazelcastStatics.INFO_CACHE);
        InfoDto infoDto = new InfoDto(info, HOST_NAME);
        infoCache.put(infoDto.id(), infoDto);
        return infoDto;
    }

    @Override
    @LogExecutionTime
    public InfoDto updateInfo(InfoDto infoDto) {
        IMap<UUID, InfoDto> infoCache = hazelcastInstance.getMap(HazelcastStatics.INFO_CACHE);
        infoCache.remove(infoDto.id());

        infoCache.put(infoDto.id(), new InfoDto(infoDto, HOST_NAME));
        return infoDto;
    }

    @Override
    @LogExecutionTime
    public InfoDto findById(UUID id) {
        IMap<UUID, InfoDto> infoCache = hazelcastInstance.getMap(HazelcastStatics.INFO_CACHE);
        return infoCache.get(id);
    }

    @Override
    @LogExecutionTime
    public List<InfoDto> findAll() {
        IMap<UUID, InfoDto> infoCache = hazelcastInstance.getMap(HazelcastStatics.INFO_CACHE);
        return new ArrayList<>(infoCache.values());
    }

    @Override
    @LogExecutionTime
    public BigInteger count() {
        IMap<UUID, InfoDto> infoCache = hazelcastInstance.getMap(HazelcastStatics.INFO_CACHE);
        return BigInteger.valueOf(infoCache.size());
    }

    @Override
    public boolean delete(UUID id) {
        if (infoByIdExists(id)) {
            IMap<UUID, InfoDto> infoCache = hazelcastInstance.getMap(HazelcastStatics.INFO_CACHE);
            infoCache.delete(id);
            return true;
        }
        return false;
    }

    @Override
    @LogExecutionTime
    public List<InfoDto> findPage(int pageSize, int pageNumber) {
        int skip = pageSize * (pageNumber - 1);
        IMap<UUID, InfoDto> infoCache = hazelcastInstance.getMap(HazelcastStatics.INFO_CACHE);
        return infoCache.values().stream().skip(skip).limit(pageSize).toList();
    }

}
