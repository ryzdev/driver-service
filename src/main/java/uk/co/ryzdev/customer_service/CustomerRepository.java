package uk.co.ryzdev.customer_service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByCreationDateAfter(LocalDateTime date);
}
