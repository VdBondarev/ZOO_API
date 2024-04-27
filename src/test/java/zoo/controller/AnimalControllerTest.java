package zoo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zoo.holder.LinksHolder.ADD_ANIMALS_TO_ANIMALS_FILE_PATH;
import static zoo.holder.LinksHolder.REMOVE_ALL_ANIMALS_FILE_PATH;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import zoo.dto.animal.AnimalResponseDto;
import zoo.dto.animal.AnimalSearchParamsRequestDto;
import zoo.model.enums.Sex;
import zoo.model.enums.Type;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnimalControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Verify that getById() endpoint works as expected with valid input id")
    @Sql(
            scripts = {
                ADD_ANIMALS_TO_ANIMALS_FILE_PATH
            },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = {
                    REMOVE_ALL_ANIMALS_FILE_PATH
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @WithMockUser(username = "user@example.com", authorities = "USER")
    void getById_ValidId_Success() throws Exception {
        MvcResult result = mockMvc.perform(get("/animals/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        AnimalResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), AnimalResponseDto.class);
        AnimalResponseDto expected = new AnimalResponseDto(
                1L,
                "Barsik",
                Type.CAT,
                Sex.MALE,
                10,
                9,
                1L
        );

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify that search() endpoint works as expected with valid input")
    @Sql(
            scripts = {
                    ADD_ANIMALS_TO_ANIMALS_FILE_PATH
            },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = {
                    REMOVE_ALL_ANIMALS_FILE_PATH
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @WithMockUser(username = "user@example.com", authorities = "USER")
    void search_ValidParams_Success() throws Exception {
        AnimalSearchParamsRequestDto requestDto = new AnimalSearchParamsRequestDto(
                "Barsik",
                "CAT",
                "MALE",
                List.of(15),
                List.of(15),
                List.of(1L)
        );

        String param = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(get("/animals/search")
                        .content(param)
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(status().isOk())
                .andReturn();

        AnimalResponseDto expectedDto = new AnimalResponseDto(
                1L,
                "Barsik",
                Type.CAT,
                Sex.MALE,
                10,
                9,
                1L
        );

        AnimalResponseDto[] expectedArray = new AnimalResponseDto[] {expectedDto};
        AnimalResponseDto[] actualArray = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), AnimalResponseDto[].class);

        assertEquals(expectedArray[0], actualArray[0]);
    }
}
