package uk.co.ryzdev.driverservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverRepository driverRepository;

    private final String firstName1 = "Tony";
    private final String lastName1 = "Stark";
    private final String dateOfBirth1 = "1970-05-29";

    private final String firstName2 = "Bruce";
    private final String lastName2 = "Banner";
    private final String dateOfBirth2 = "1962-05-01";

    @Test
    public void createDriver() throws Exception {
        when(driverRepository.save(any())).thenReturn(new Driver(firstName1, lastName1, LocalDate.parse(dateOfBirth1)));

        this.mockMvc.perform(post("/driver/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\n" +
                        "  \"dateOfBirth\": \"%s\",\n" +
                        "  \"firstName\": \"%s\",\n" +
                        "  \"lastName\": \"%s\"\n" +
                        "}", dateOfBirth1, firstName1, lastName1))).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(firstName1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(lastName1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value(dateOfBirth1));

    }

    @Test
    public void getAll() throws Exception {
        when(driverRepository.findAll()).thenReturn(Arrays.asList(
                new Driver(firstName1, lastName1, LocalDate.parse(dateOfBirth1)),
                new Driver(firstName2, lastName2, LocalDate.parse(dateOfBirth2))));

        this.mockMvc.perform(get("/drivers")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value(firstName1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value(lastName1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateOfBirth").value(dateOfBirth1))

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value(firstName2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value(lastName2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dateOfBirth").value(dateOfBirth2));
    }

    @Test
    public void getByDate() throws Exception {
        when(driverRepository.findByCreationDateAfter(any())).thenReturn(Collections.singletonList(
                new Driver(firstName2, lastName2, LocalDate.parse(dateOfBirth2))));

        this.mockMvc.perform(get(String.format("/drivers/byDate?date=%s", dateOfBirth2))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value(firstName2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value(lastName2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateOfBirth").value(dateOfBirth2));
    }
}
