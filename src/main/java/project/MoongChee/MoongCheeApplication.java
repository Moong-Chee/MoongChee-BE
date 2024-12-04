package project.MoongChee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MoongCheeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoongCheeApplication.class, args);
    }

}
