package animals.service;

import static animals.constants.NumbersConstantsHolder.ONE;

import animals.dto.animal.AnimalResponseDto;
import animals.mapper.AnimalMapper;
import animals.model.Animal;
import animals.repository.AnimalRepository;
import animals.service.parser.FileParser;
import animals.service.parser.FileParserStrategy;
import animals.service.reader.FileReader;
import animals.service.reader.FileReaderStrategy;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;
    private final FileReaderStrategy readerStrategy;
    private final FileParserStrategy parserStrategy;
    private final AnimalMapper animalMapper;

    @Transactional
    @Override
    public List<AnimalResponseDto> create(MultipartFile file) {
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
