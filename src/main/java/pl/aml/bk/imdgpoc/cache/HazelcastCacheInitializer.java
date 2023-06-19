package pl.aml.bk.imdgpoc.cache;


import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.cp.lock.FencedLock;
import com.hazelcast.map.IMap;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Profile;
import pl.aml.bk.imdgpoc.common.logging.LogExecutionTime;
import pl.aml.bk.imdgpoc.controller.model.InfoDto;

import java.net.InetAddress;
import java.util.UUID;

@AllArgsConstructor
@Profile("hazelcast")
public class HazelcastCacheInitializer implements HazelcastInstanceAware, CacheInitializer {

    private static final String INITIALIZATION_LOCK = "initializationLock";

    private HazelcastInstance hazelcastInstance;

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @SneakyThrows
    @LogExecutionTime
    public void initializeCache() {
        IMap<UUID, InfoDto> cacheMap = hazelcastInstance.getMap(HazelcastStatics.INFO_CACHE);
        FencedLock initializationLock = hazelcastInstance.getCPSubsystem().getLock(INITIALIZATION_LOCK);
        if (initializationLock.tryLock()) {
            try {
                if (cacheMap.isEmpty()) {
                    InfoDto infoDto = new InfoDto("Test info 1", InetAddress.getLocalHost().getHostName());
                    cacheMap.put(infoDto.id(), infoDto);
                    Thread.sleep(1000);
                }
            } finally {
                initializationLock.unlock();
            }
        }

    }

}
