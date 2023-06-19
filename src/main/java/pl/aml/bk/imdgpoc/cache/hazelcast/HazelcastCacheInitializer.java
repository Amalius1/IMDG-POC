package pl.aml.bk.imdgpoc.cache.hazelcast;


import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.cp.lock.FencedLock;
import com.hazelcast.map.IMap;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pl.aml.bk.imdgpoc.cache.CacheInitializer;
import pl.aml.bk.imdgpoc.common.logging.LogExecutionTime;
import pl.aml.bk.imdgpoc.controller.model.InfoDto;

import java.net.InetAddress;
import java.util.UUID;

import static pl.aml.bk.imdgpoc.cache.hazelcast.HazelcastStatics.INITIALIZATION_LOCK;

@AllArgsConstructor
@Slf4j
public class HazelcastCacheInitializer implements HazelcastInstanceAware, CacheInitializer {


    private HazelcastInstance hazelcastInstance;

    private Integer initialCount;

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
                    log.info("Started initialization on host {}", InetAddress.getLocalHost().getHostName());
                    for (int i = 0; i < initialCount; i++) {
                        InfoDto infoDto = new InfoDto("Random info number: " + i, InetAddress.getLocalHost().getHostName());
                        cacheMap.put(infoDto.id(), infoDto);
                    }
                }
            } catch (Exception e) {
                log.error("Exception during cache initialization", e);
            } finally {
                initializationLock.unlock();
            }
        }
    }

}
