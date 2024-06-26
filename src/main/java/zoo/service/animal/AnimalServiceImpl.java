package zoo.service.animal;

import static zoo.constants.NumbersConstantsHolder.ONE;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import zoo.dto.animal.AnimalResponseDto;
import zoo.dto.animal.AnimalSearchParamsRequestDto;
import zoo.mapper.AnimalMapper;
import zoo.model.Animal;
import zoo.repository.AnimalRepository;
import zoo.repository.specification.SpecificationBuilder;
import zoo.service.parser.FileParser;
import zoo.service.parser.FileParserStrategy;
import zoo.service.reader.FileReader;
import zoo.service.reader.FileReaderStrategy;

@RequiredArgsConstructor
@Service
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;
    private final FileReaderStrategy readerStrategy;
    private final FileParserStrategy parserStrategy;
    private final AnimalMapper animalMapper;
    private final SpecificationBuilder<Animal, AnimalSearchParamsRequestDto> specificationBuilder;

    @Transactional
    @Override
    public List<AnimalResponseDto> upload(MultipartFile file) {
        String fileType = getFileType(file);
        FileReader fileReader = readerStrategy.getFileReader(fileType);
        List<String[]> animalsRecords = fileReader.readFromFile(file);
        FileParser fileParser = parserStrategy.getFileParser(fileType);
        List<Animal> animals = fileParser.parse(animalsRecords);
        animalRepository.saveAll(animals);
        return animals
                .stream()
                .map(animalMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public AnimalResponseDto getById(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find an animal by id " + id));
        return animalMapper.toResponseDto(animal);
    }

    @Override
    public List<AnimalResponseDto> search(
            AnimalSearchParamsRequestDto searchParams, Pageable pageable) {
        if (isEmpty(searchParams)) {
            throw new IllegalArgumentException("Searching should be done by at least 1 param");
        }
        return animalRepository.findAll(
                specificationBuilder.build(searchParams), pageable)
                .stream()
                .map(animalMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    private boolean isEmpty(AnimalSearchParamsRequestDto searchParams) {
        return searchParams == null
               || ((searchParams.name() == null || searchParams.name().isEmpty())
                && (searchParams.sex() == null || searchParams.sex().isEmpty())
                && (searchParams.type() == null || searchParams.type().isEmpty())
                && (searchParams.categoriesIds() == null)
                && (searchParams.cost() == null)
                && (searchParams.weight() == null));
    }

    private String getFileType(MultipartFile file) {
        if (file.isEmpty()) {
            return "Upload a not empty file";
        }
        String[] splitByDot = file.getOriginalFilename().split("\\.");
        if (splitByDot.length == ONE) {
            return """
                    File should have proper type.
                    For example, CSV or XML.
                    """;
        }
        return splitByDot[splitByDot.length - ONE];
    }
}
