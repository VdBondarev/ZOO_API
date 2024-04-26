package animals.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import animals.dto.animal.AnimalResponseDto;
import animals.dto.animal.AnimalSearchParamsRequestDto;
import animals.mapper.AnimalMapper;
import animals.model.Animal;
import animals.model.enums.Sex;
import animals.model.enums.Type;
import animals.repository.AnimalRepository;
import animals.repository.specification.SpecificationBuilder;
import animals.service.parser.CsvFileParser;
import animals.service.parser.FileParserStrategy;
import animals.service.parser.XmlFileParser;
import animals.service.reader.CsvFileReader;
import animals.service.reader.FileReaderStrategy;
import java.util.List;

import animals.service.reader.XmlFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class AnimalServiceImplTest {
    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private FileReaderStrategy readerStrategy;
    @Mock
    private FileParserStrategy parserStrategy;
    @Mock
    private AnimalMapper animalMapper;
    @Mock
    private SpecificationBuilder<Animal, AnimalSearchParamsRequestDto> specificationBuilder;
    @InjectMocks
    private AnimalServiceImpl animalService;
    private Animal buddyAnimal;
    private Animal kittyAnimal;
    private AnimalResponseDto buddyResponseDto;
    private AnimalResponseDto kittyResponseDto;

    @BeforeEach
    void setUp() {
        buddyAnimal = createAnimal(
                1L, "Buddy", "cat", "female", 51, 25, 2L
        );
        kittyAnimal = createAnimal(
                2L, "Kitty", "dog", "male", 33, 111, 4L
        );

        buddyResponseDto = createResponseDto(buddyAnimal);
        kittyResponseDto = createResponseDto(kittyAnimal);
    }

    @Test
    @DisplayName("Verify that upload() method works fine for csv file")
    void upload_CsvContent_ReturnsValidResponse() {
        String csvContent = """
                Name,Type,Sex,Weight,Cost
                Buddy,cat,female,51,25
                Cooper,,female,46,25
                Kitty,dog,male,33,111
                mUHA,dog,female,12,22
                """; // don't ask why Kitty is a dog :)

        MultipartFile csvFile;
        csvFile = new MockMultipartFile("test.csv", "test.csv",
                "text/plain", csvContent.getBytes());

        String[] buddyInfo = new String[] {"Buddy", "cat", "female", "51", "25"};
        String[] kittyInfo = new String[] {"Kitty", "dog", "male", "33", "111"};

        // expecting only 2 animals passed the validation
        List<String[]> expectedReadList;
        expectedReadList = List.of(buddyInfo,kittyInfo);

        CsvFileReader mockedReader;
        mockedReader = mock(CsvFileReader.class);

        CsvFileParser mockedParser;
        mockedParser = mock(CsvFileParser.class);

        List<Animal> expectedAnimalList;
        expectedAnimalList = List.of(buddyAnimal, kittyAnimal);

        List<AnimalResponseDto> expected;
        expected = List.of(buddyResponseDto, kittyResponseDto);

        when(readerStrategy.getFileReader("csv")).thenReturn(mockedReader);
        when(mockedReader.readFromFile(csvFile)).thenReturn(expectedReadList);
        when(parserStrategy.getFileParser("csv")).thenReturn(mockedParser);
        when(mockedParser.parse(expectedReadList)).thenReturn(expectedAnimalList);
        when(animalRepository.saveAll(expectedAnimalList)).thenReturn(expectedAnimalList);
        when(animalMapper.toResponseDto(buddyAnimal)).thenReturn(buddyResponseDto);
        when(animalMapper.toResponseDto(kittyAnimal)).thenReturn(kittyResponseDto);

        List<AnimalResponseDto> actual = animalService.upload(csvFile);

        assertEquals(expected, actual);
        assertEquals(2, actual.size());
    }

    @Test
    @DisplayName("Verify that upload() method works fine for xml file")
    void upload_XmlContent_ReturnsValidResponse() {
        String xmlContent = """
            <?xml version="1.0" encoding="UTF-8"?>
            <animals>
                <animal>
                    <Name>Buddy</Name>
                    <Type>cat</Type>
                    <Sex>female</Sex>
                    <Weight>51</Weight>
                    <Cost>25</Cost>
                </animal>
                <animal>
                    <Name>Cooper</Name>
                    <Type></Type>
                    <Sex>female</Sex>
                    <Weight>46</Weight>
                    <Cost>25</Cost>
                </animal>
                <animal>
                    <Name>Kitty</Name>
                    <Type>dog</Type>
                    <Sex>male</Sex>
                    <Weight>33</Weight>
                    <Cost>111</Cost>
                </animal>
                <animal>
                    <Name>mUHA</Name>
                    <Type>dog</Type>
                    <Sex>female</Sex>
                    <Weight>12</Weight>
                    <Cost>22</Cost>
                </animal>
            </animals>
                """;

        MultipartFile xmlFile = new MockMultipartFile("test.xml", "test.xml",
                "text/xml", xmlContent.getBytes());

        String[] buddyInfo = new String[] {"Buddy", "cat", "female", "51", "25"};
        String[] kittyInfo = new String[] {"Kitty", "dog", "male", "33", "111"};

        List<String[]> expectedReadList;
        expectedReadList = List.of(buddyInfo, kittyInfo);

        XmlFileReader mockedReader;
        mockedReader = mock(XmlFileReader.class);

        XmlFileParser mockedParser;
        mockedParser = mock(XmlFileParser.class);

        List<Animal> expectedAnimalList;
        expectedAnimalList = List.of(buddyAnimal, kittyAnimal);

        List<AnimalResponseDto> expected;
        expected = List.of(buddyResponseDto, kittyResponseDto);

        when(readerStrategy.getFileReader("xml")).thenReturn(mockedReader);
        when(mockedReader.readFromFile(xmlFile)).thenReturn(expectedReadList);
        when(parserStrategy.getFileParser("xml")).thenReturn(mockedParser);
        when(mockedParser.parse(expectedReadList)).thenReturn(expectedAnimalList);
        when(animalRepository.saveAll(expectedAnimalList)).thenReturn(expectedAnimalList);
        when(animalMapper.toResponseDto(buddyAnimal)).thenReturn(buddyResponseDto);
        when(animalMapper.toResponseDto(kittyAnimal)).thenReturn(kittyResponseDto);

        List<AnimalResponseDto> actual = animalService.upload(xmlFile);

        assertEquals(expected, actual);
        assertEquals(2, actual.size());
    }

    private AnimalResponseDto createResponseDto(Animal animal) {
        return new AnimalResponseDto(
                animal.getId(),
                animal.getName(),
                animal.getType(),
                animal.getSex(),
                animal.getWeight(),
                animal.getCost(),
                animal.getCategoryId()
        );
    }

    private Animal createAnimal(
            long id, String name, String type, String sex, int weight, int cost, Long categoryId) {
        return new Animal()
                .setCategoryId(categoryId)
                .setId(id)
                .setType(Type.fromString(type))
                .setSex(Sex.fromString(sex))
                .setName(name)
                .setWeight(weight)
                .setCost(cost);
    }
}
