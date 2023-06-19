package pl.aml.bk.imdgpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ImdgPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImdgPocApplication.class, args);
    }

}
