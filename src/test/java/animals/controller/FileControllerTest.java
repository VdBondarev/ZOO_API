package animals.controller;

import static animals.holder.LinksHolder.CSV_FILE_PATH;
import static animals.holder.LinksHolder.REMOVE_ALL_ANIMALS_FILE_PATH;
import static animals.holder.LinksHolder.WORD_FILE_PATH;
import static animals.holder.LinksHolder.XML_FILE_PATH;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileControllerTest {
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

    @WithMockUser(username = "admin@example.com", authorities = "ADMIN")
    @Test
    @DisplayName("Verify that upload() method works as expected with .csv file")
    @Sql(
            scripts = REMOVE_ALL_ANIMALS_FILE_PATH,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void upload_CsvFile_Success() throws Exception {
        File file = new File(CSV_FILE_PATH);

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

        int lengthExpected = 8;
        Assertions.assertEquals(lengthExpected, animalResponseDtos.length);
    }

    @WithMockUser(username = "admin@example.com", authorities = "ADMIN")
    @Test
    @DisplayName("Verify that upload() method works as expected with .xml file")
    @Sql(
            scripts = REMOVE_ALL_ANIMALS_FILE_PATH,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void upload_XmlFile_Success() throws Exception {
        File file = new File(XML_FILE_PATH);

        byte[] fileContent = Files.readAllBytes(file.toPath());

        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", file.getName(), "text/xml", fileContent);

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

    @WithMockUser(username = "admin@example.com", authorities = "ADMIN")
    @Test
    @DisplayName("Verify that upload() method works as expected with .word file")
    @Sql(
            scripts = REMOVE_ALL_ANIMALS_FILE_PATH,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void upload_workFile_Fail() throws Exception {
        File file = new File(WORD_FILE_PATH);

        byte[] fileContent = Files.readAllBytes(file.toPath());

        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", file.getName(), "text/xml", fileContent);

        MvcResult result = mockMvc.perform(
                        multipart("/files/upload")
                                .file(multipartFile)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
