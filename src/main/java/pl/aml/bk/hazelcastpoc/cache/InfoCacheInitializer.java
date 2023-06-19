package pl.aml.bk.hazelcastpoc.cache;


import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.cp.lock.FencedLock;
import com.hazelcast.map.IMap;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import pl.aml.bk.hazelcastpoc.controller.model.InfoDto;

import java.net.InetAddress;
import java.util.UUID;

@AllArgsConstructor
public class InfoCacheInitializer implements HazelcastInstanceAware {

    private HazelcastInstance hazelcastInstance;

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @SneakyThrows
    public void initializeCache() {
        IMap<UUID, InfoDto> cacheMap = hazelcastInstance.getMap("infoCache");
        FencedLock initializationLock = hazelcastInstance.getCPSubsystem().getLock("initializationLock");
        try {
            boolean tryLock = initializationLock.tryLock();
            if (tryLock && cacheMap.isEmpty()) {
                InfoDto infoDto = new InfoDto("Test info 1", InetAddress.getLocalHost().getHostName());
                cacheMap.put(infoDto.id(), infoDto);
            }
        } finally {
            initializationLock.unlock();
        }

    }

}
