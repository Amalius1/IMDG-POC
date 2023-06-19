package pl.aml.bk.hazelcastpoc.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheInitializationRunner implements CommandLineRunner {

    private final InfoCacheInitializer infoCacheInitializer;

    @Override
    public void run(String... args) {
        infoCacheInitializer.initializeCache();

    }
}
