package uk.co.ryzdev.driverservice;

import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class DriverController {

    public DriverRepository repository;

    public DriverController(DriverRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/driver/create")
    public ResponseEntity<?> create(@RequestBody Driver driver) {
        return new ResponseEntity<>(repository.save(driver), HttpStatus.CREATED);
    }

    @GetMapping("/drivers")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/drivers/byDate")
    public ResponseEntity<?>  getByDate(@RequestParam("date")
                                  @ApiParam(example = "2021-03-15")
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return new ResponseEntity<>(repository.findByCreationDateAfter(date.atStartOfDay()), HttpStatus.OK);
    }
}

