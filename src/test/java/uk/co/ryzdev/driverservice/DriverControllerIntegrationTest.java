package uk.co.ryzdev.driverservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(LoadTestDatabase.class)
class DriverControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldGetAll() {
        String url = "http://localhost:" + port + "/drivers";
        ResponseEntity<Driver[]> driversResponse = this.restTemplate.getForEntity(url, Driver[].class);
        List<Driver> drivers = Arrays.asList(Objects.requireNonNull(driversResponse.getBody()));

        assertThat(drivers.size()).isEqualTo(2);

        assertThat(drivers.get(0).getFirstName()).isEqualTo("Luke");
        assertThat(drivers.get(0).getLastName()).isEqualTo("Skywalker");
        assertThat(drivers.get(0).getDateOfBirth()).isEqualTo(LocalDate.of(1900, 12, 24));

        assertThat(drivers.get(1).getFirstName()).isEqualTo("Leia");
        assertThat(drivers.get(1).getLastName()).isEqualTo("Organa");
        assertThat(drivers.get(1).getDateOfBirth()).isEqualTo(LocalDate.of(1900, 12, 25));
    }

    @Test
    public void shouldPostNewDriver() throws Exception {
        String url = "http://localhost:" + port + "/driver/create";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("dateOfBirth", "1939-05-01");
        map.add("firstName", "Bruce");
        map.add("lastName", "Wayne");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
//        HttpEntity<String> request = new HttpEntity<>(
//                "{\n" +
//                        "  \"dateOfBirth\": \"1939-05-01\",\n" +
//                        "  \"firstName\": \"Bruce\",\n" +
//                        "  \"lastName\": \"Wayne\"\n" +
//                        "}");
        ResponseEntity<Driver> driversResponse = this.restTemplate.postForEntity(url, request, Driver.class);
        Driver driver = Objects.requireNonNull(driversResponse.getBody());
        assertThat(driversResponse.getBody().getLastName()).isEqualTo("");

        assertThat(driver.getLastName()).isEqualTo("Wayne");
    }

//    @Test
//    void name() {
//
//        System.out.println(new Driver("foo", "bar", LocalDate.of(1900, 01, 01)).toString());
//    }
}