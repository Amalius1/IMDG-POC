package pl.aml.bk.imdgpoc.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheInitializationRunner implements CommandLineRunner {

    private final CacheInitializer infoCacheInitializer;

    @Override
    public void run(String... args) {
        infoCacheInitializer.initializeCache();

    }
}
