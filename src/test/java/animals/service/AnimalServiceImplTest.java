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
import animals.service.reader.CsvFileReader;
import animals.service.reader.FileReaderStrategy;
import java.util.List;
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

    @Test
    void create_ValidCsvContent_ReturnsValidResponse() {
        String csvContent = """
                Name,Type,Sex,Weight,Cost
                Buddy,cat,female,51,25
                Cooper,,female,46,25
                Kitty,dog,male,33,111
                mUHA,dog,female,12,22
                """; // don't ask why Kitty is a dog :)

        MultipartFile file = new MockMultipartFile("test.csv", "test.csv",
                "text/plain", csvContent.getBytes());

        String[] buddyInfo = new String[] {"Buddy", "cat", "female", "51", "25"};
        String[] cooperInfo = new String[] {"Cooper", "", "female", "46", "25"};
        String[] kittyInfo = new String[] {"Kitty", "dog", "male", "33", "111"};
        String[] muhaInfo = new String[] {"mUHA", "dog", "female", "12", "22"};

        List<String[]> expectedReadList = List.of(buddyInfo, cooperInfo, kittyInfo, muhaInfo);

        CsvFileReader mockedReader = mock(CsvFileReader.class);

        CsvFileParser mockedParser = mock(CsvFileParser.class);

        Animal buddyAnimal = createAnimal(
                1L, "Buddy", "cat", "female", 51, 25, 2L
        );
        Animal kittyAnimal = createAnimal(
                2L, "Kitty", "dog", "male", 33, 111, 4L
        );

        List<Animal> expectedAnimalList = List.of(buddyAnimal, kittyAnimal);

        AnimalResponseDto buddyResponseDto = createResponseDto(buddyAnimal);
        AnimalResponseDto kittyResponseDto = createResponseDto(kittyAnimal);

        List<AnimalResponseDto> expected;
        expected = List.of(buddyResponseDto, kittyResponseDto);

        when(readerStrategy.getFileReader("csv")).thenReturn(mockedReader);
        when(mockedReader.readFromFile(file)).thenReturn(expectedReadList);
        when(parserStrategy.getFileParser("csv")).thenReturn(mockedParser);
        when(mockedParser.parse(expectedReadList)).thenReturn(expectedAnimalList);
        when(animalRepository.saveAll(expectedAnimalList)).thenReturn(expectedAnimalList);
        when(animalMapper.toResponseDto(buddyAnimal)).thenReturn(buddyResponseDto);
        when(animalMapper.toResponseDto(kittyAnimal)).thenReturn(kittyResponseDto);

        List<AnimalResponseDto> actual = animalService.create(file);

        assertEquals(expected, actual);
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