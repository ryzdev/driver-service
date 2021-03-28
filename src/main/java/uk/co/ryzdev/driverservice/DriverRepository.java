package uk.co.ryzdev.driverservice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

interface DriverRepository extends JpaRepository<Driver, Long> {

    List<Driver> findByCreationDateAfter(LocalDateTime date);
}
