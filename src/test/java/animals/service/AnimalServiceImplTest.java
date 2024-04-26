package animals.service;

import static animals.constants.CriteriaQueryConstantsHolder.COST_COLUMN;
import static animals.constants.CriteriaQueryConstantsHolder.TYPE_COLUMN;
import static animals.constants.CriteriaQueryConstantsHolder.WEIGHT_COLUMN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import animals.service.reader.XmlFileReader;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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

        MultipartFile xmlFile;
        xmlFile = new MockMultipartFile("test.xml", "test.xml",
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

    @Test
    @DisplayName("Verify that getById works as expected with a valid id")
    void getById_ValidId_ReturnsValidResponse() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(buddyAnimal));
        when(animalMapper.toResponseDto(buddyAnimal)).thenReturn(buddyResponseDto);

        AnimalResponseDto expected = buddyResponseDto;
        AnimalResponseDto actual = animalService.getById(1L);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify that getById works as expected with a non-valid id")
    void getById_NonValidId_ThrowsException() {
        when(animalRepository.findById(3L)).thenReturn(Optional.empty());

        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> animalService.getById(3L));

        String expected = "Can't find an animal by id 3";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify that search() works fine with valid params")
    void search_ValidParams_ReturnsValidResponse() {
        AnimalSearchParamsRequestDto paramsRequestDto =
                createAnimalSearchParametersDto(
                        null, "cat", null, List.of(55), List.of(50), null);

        Specification<Animal> typeSpecification = (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get(TYPE_COLUMN), paramsRequestDto.type());

        Specification<Animal> weightSpecification = (root, query, criteriaBuilder)
                -> criteriaBuilder.between(
                        root.get(WEIGHT_COLUMN), 0, paramsRequestDto.weight().get(0));

        Specification<Animal> costSpecification = (root, query, criteriaBuilder)
                -> criteriaBuilder.between(
                        root.get(COST_COLUMN), 0, paramsRequestDto.cost().get(0));

        Specification<Animal> specification;
        specification = typeSpecification.and(weightSpecification).and(costSpecification);

        PageRequest pageable = PageRequest.of(0, 5);
        Page<Animal> page = new PageImpl<>(
                List.of(buddyAnimal),
                pageable,
                List.of(buddyAnimal).size());

        AnimalResponseDto expected = buddyResponseDto;

        when(specificationBuilder.build(paramsRequestDto)).thenReturn(specification);
        when(animalRepository.findAll(specification, pageable)).thenReturn(page);
        when(animalMapper.toResponseDto(buddyAnimal)).thenReturn(expected);

        List<AnimalResponseDto> expectedList = List.of(expected);
        List<AnimalResponseDto> actualList = animalService.search(paramsRequestDto, pageable);

        assertEquals(expectedList, actualList);
        assertEquals(expected, actualList.get(0));
    }

    @Test
    @DisplayName("Verify that search() throws an exception when passing non-valid params")
    void search_NullPassedParams_ThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> animalService.search(null, PageRequest.of(0, 5)));

        String expected = "Searching should be done by at least 1 param";
        String actual = exception.getMessage();

        assertEquals(expected, actual);

        exception = assertThrows(IllegalArgumentException.class, () -> animalService.search(
                createAnimalSearchParametersDto(
                        null, null, null, null, null, null
                ), PageRequest.of(0, 5)));

        actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    private AnimalSearchParamsRequestDto createAnimalSearchParametersDto(
            String name, String type, String sex,
            List<Integer> weight, List<Integer> cost, List<Long> categoriesIds) {
        return new AnimalSearchParamsRequestDto(
                name,
                type,
                sex,
                weight,
                cost,
                categoriesIds
        );
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
