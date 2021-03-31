package uk.co.ryzdev.customer_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CustomerRepository repository) {

        return args -> {
            log.info("Preloading " + repository
                    .save(new Customer("Luke", "Skywalker", LocalDate.of(1958, 12, 24))));
            log.info("Preloading " + repository
                    .save(new Customer("Leia", "Organa", LocalDate.of(1958, 12, 25))));
        };
    }
}
