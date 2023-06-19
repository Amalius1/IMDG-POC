package pl.aml.bk.hazelcastpoc.config;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.aml.bk.hazelcastpoc.cache.InfoCacheInitializer;

import static com.hazelcast.core.Hazelcast.newHazelcastInstance;

@Configuration
@Profile("hazelcast")
public class HazelcastConfiguration {

    @Bean
    @Profile("kubernetes")
    public Config hazelcastConfigK8s() {
        Config config = new Config();
        config.getMapConfig("infoCache")
                .setBackupCount(1)
                .setAsyncBackupCount(0);
        config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getKubernetesConfig().setEnabled(true)
                .setProperty("namespace", "default")
                .setProperty("service-name", "hazelcast-poc");

        return config;
    }

    @Bean
    @Profile("local")
    public Config hazelcastConfigLocal() {
        Config config = new Config();
        config.getMapConfig("infoCache")
                .setBackupCount(1)
                .setAsyncBackupCount(0);

        return config;
    }

    @Bean
    @Qualifier("mainInstance")
    public HazelcastInstance hazelcastInstance(Config hazelcastConfig) {
        return newHazelcastInstance(hazelcastConfig);
    }


    @Bean
    public InfoCacheInitializer infoCacheInitializer(@Qualifier("mainInstance") HazelcastInstance hazelcastInstance) {
        return new InfoCacheInitializer(hazelcastInstance);
    }


}
