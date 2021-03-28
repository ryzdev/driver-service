package uk.co.ryzdev.driverservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@TestConfiguration
public class LoadTestDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadTestDatabase.class);

    @Bean
    CommandLineRunner initDatabase(DriverRepository repository) {

        return args -> {
            log.info("Preloading " + repository
                    .save(new Driver("Luke", "Skywalker", LocalDate.of(1900, 12, 24))));
            log.info("Preloading " + repository
                    .save(new Driver("Leia", "Organa", LocalDate.of(1900, 12, 25))));
        };
    }
}
