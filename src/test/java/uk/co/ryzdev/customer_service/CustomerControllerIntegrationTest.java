package uk.co.ryzdev.customer_service;

import org.junit.jupiter.api.Disabled;
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
class CustomerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldGetAll() {
        String url = "http://localhost:" + port + "/customers";
        ResponseEntity<Customer[]> driversResponse = this.restTemplate.getForEntity(url, Customer[].class);
        List<Customer> customers = Arrays.asList(Objects.requireNonNull(driversResponse.getBody()));

        assertThat(customers.size()).isEqualTo(2);

        assertThat(customers.get(0).getFirstName()).isEqualTo("Luke");
        assertThat(customers.get(0).getLastName()).isEqualTo("Skywalker");
        assertThat(customers.get(0).getDateOfBirth()).isEqualTo(LocalDate.of(1958, 12, 24));

        assertThat(customers.get(1).getFirstName()).isEqualTo("Leia");
        assertThat(customers.get(1).getLastName()).isEqualTo("Organa");
        assertThat(customers.get(1).getDateOfBirth()).isEqualTo(LocalDate.of(1958, 12, 25));
    }

    @Disabled // TODO fix this test
    @Test
    public void shouldPostNewDriver() throws Exception {
        String url = "http://localhost:" + port + "/customer/create";

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
        ResponseEntity<Customer> driversResponse = this.restTemplate.postForEntity(url, request, Customer.class);
        Customer customer = Objects.requireNonNull(driversResponse.getBody());
        assertThat(driversResponse.getBody().getLastName()).isEqualTo("");

        assertThat(customer.getLastName()).isEqualTo("Wayne");
    }
}