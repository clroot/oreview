package io.clroot.oreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OreviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(OreviewApplication.class, args);
    }

}
