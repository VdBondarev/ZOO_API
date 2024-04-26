package animals.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import animals.dto.animal.AnimalResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileControllerTest {
    protected static MockMvc mockMvc;
    private static final String VALID_CSV_FILE_PATH = "src/test/java/files/test.csv";
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "admin@example.com", authorities = "ADMIN")
    @Test
    @DisplayName("Verify that upload() method works as expected with .csv file")
    void upload_CsvFile_Success() throws Exception {
        File file = new File(VALID_CSV_FILE_PATH);

        byte[] fileContent = Files.readAllBytes(file.toPath());

        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", file.getName(), "text/csv", fileContent);

        MvcResult result = mockMvc.perform(
                multipart("/files/upload")
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isCreated())
                .andReturn();

        AnimalResponseDto[] animalResponseDtos = objectMapper.readValue(result.getResponse()
                .getContentAsString(), AnimalResponseDto[].class);

        int lengthExpected = 7;
        Assertions.assertEquals(lengthExpected, animalResponseDtos.length);
    }
}
