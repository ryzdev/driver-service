package uk.co.ryzdev.driverservice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Driver {

    @ApiModelProperty(hidden = true)
    private @Id @GeneratedValue long id;

    @ApiModelProperty(hidden = true)
    private @CreationTimestamp LocalDateTime creationDate;

    @ApiModelProperty(example = "Bruce")
    private String firstName;

    @ApiModelProperty(example = "Wayne")
    private String lastName;

    @ApiModelProperty(example = "1939-05-01")
    private @JsonFormat(pattern="yyyy-MM-dd") LocalDate dateOfBirth;

    public Driver() {
    }

    public Driver(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
