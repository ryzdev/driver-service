package uk.co.ryzdev.customer_service;

import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class CustomerController {

    public CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/customer/create")
    public ResponseEntity<?> create(@RequestBody Customer customer) {
        return new ResponseEntity<>(repository.save(customer), HttpStatus.CREATED);
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/customers/byDate")
    public ResponseEntity<?>  getByDate(@RequestParam("date")
                                  @ApiParam(example = "2021-03-15")
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return new ResponseEntity<>(repository.findByCreationDateAfter(date.atStartOfDay()), HttpStatus.OK);
    }
}

