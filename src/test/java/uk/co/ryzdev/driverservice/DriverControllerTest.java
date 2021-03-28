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

    @Test
    public void createDriver() throws Exception {
        when(driverRepository.save(any())).thenReturn(new Driver("foo", "bar", LocalDate.of(1900, 01, 01)));

        this.mockMvc.perform(post("/driver/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"dateOfBirth\": \"1939-05-01\",\n" +
                        "  \"firstName\": \"Bruce\",\n" +
                        "  \"lastName\": \"Wayne\"\n" +
                        "}")).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("foo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("bar"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value("1900-01-01"));
    }

    @Test
    public void getAll() throws Exception {
        when(driverRepository.findAll()).thenReturn(Arrays.asList(new Driver("foo", "bar", LocalDate.of(1900, 01, 01))));

        this.mockMvc.perform(get("/drivers")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("foo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("bar"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateOfBirth").value("1900-01-01"));
    }

    @Test
    public void getByDate() throws Exception {
        when(driverRepository.findByCreationDateAfter(any())).thenReturn(Arrays.asList(new Driver("foo", "bar", LocalDate.of(1900, 01, 01))));

        this.mockMvc.perform(get("/drivers/byDate?date=1900-01-01")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("foo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("bar"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateOfBirth").value("1900-01-01"));
    }
}
