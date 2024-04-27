package zoo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zoo.holder.LinksHolder.ADD_USER_TO_DATABASE_FILE_PATH;
import static zoo.holder.LinksHolder.REMOVE_ALL_USERS_FILE_PATH;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import zoo.dto.user.UserRegistrationRequestDto;
import zoo.dto.user.UserResponseDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {
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

    @Sql(
            scripts = { REMOVE_ALL_USERS_FILE_PATH },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = { REMOVE_ALL_USERS_FILE_PATH },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("Verify that registration endpoint works as expected with valid input")
    @Test
    void register_ValidInput_Success() throws Exception {
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto(
                "test@email.com",
                "Test",
                "Test",
                "12345678",
                "12345678");

        String content = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/auth/register")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        UserResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), UserResponseDto.class);

        UserResponseDto expected = createUserResponseDto(1L, requestDto);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Verify that registration endpoint works as expected when passing a registered email
            """)
    @Sql(
            scripts = { ADD_USER_TO_DATABASE_FILE_PATH },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = { REMOVE_ALL_USERS_FILE_PATH },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void register_AlreadyExistingEmailIsPassed_Failure() throws Exception {
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto(
                "admin@example.com",
                "Test",
                "Test",
                "12345678",
                "12345678");

        String content = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/auth/register")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        String expectedMessage = """
                User with passed email already registered
                Try another one
                """;

        String actualMessage = result.getResolvedException().getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    private UserResponseDto createUserResponseDto(Long id, UserRegistrationRequestDto requestDto) {
        return new UserResponseDto(
                id,
                requestDto.email(),
                requestDto.firstName(),
                requestDto.lastName());
    }
}
