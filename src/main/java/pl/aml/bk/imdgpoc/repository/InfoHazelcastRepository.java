package pl.aml.bk.imdgpoc.repository;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pl.aml.bk.imdgpoc.controller.model.InfoDto;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("hazelcast")
public class InfoHazelcastRepository implements InfoRepository {

    private static final String INFO_CACHE = "infoCache";
    private final HazelcastInstance hazelcastInstance;
    private final String HOST_NAME;

    @SneakyThrows
    public InfoHazelcastRepository(@Qualifier("mainInstance") HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
        this.HOST_NAME = InetAddress.getLocalHost().getHostName();
    }

    @Override
    public boolean infoByIdExists(UUID id) {
        IMap<UUID, InfoDto> infoCache = hazelcastInstance.getMap(INFO_CACHE);
        return infoCache.get(id) != null;
    }

    @Override
    public InfoDto add(String info) {
        IMap<UUID, InfoDto> infoCache = hazelcastInstance.getMap(INFO_CACHE);
        InfoDto infoDto = new InfoDto(info, HOST_NAME);
        infoCache.put(infoDto.id(), infoDto);
        return infoDto;
    }

    @Override
    public InfoDto updateInfo(InfoDto infoDto) {
        IMap<UUID, InfoDto> infoCache = hazelcastInstance.getMap(INFO_CACHE);
        infoCache.remove(infoDto.id());

        infoCache.put(infoDto.id(), new InfoDto(infoDto, HOST_NAME));
        return infoDto;
    }

    @Override
    public InfoDto findById(UUID id) {
        IMap<UUID, InfoDto> infoCache = hazelcastInstance.getMap(INFO_CACHE);
        return infoCache.get(id);
    }

    @Override
    public List<InfoDto> findAll() {
        IMap<UUID, InfoDto> infoCache = hazelcastInstance.getMap(INFO_CACHE);
        return new ArrayList<>(infoCache.values());
    }

}
