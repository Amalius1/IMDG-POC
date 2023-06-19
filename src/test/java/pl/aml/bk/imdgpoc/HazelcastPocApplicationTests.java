package pl.aml.bk.imdgpoc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"local", "hazelcast"})
class HazelcastPocApplicationTests {

    @Test
    void contextLoads() {
    }

}
