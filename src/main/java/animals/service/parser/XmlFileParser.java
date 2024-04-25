package animals.service.parser;

import static animals.constants.FilesRelatedConstantsHolder.XML;

import animals.model.Animal;
import animals.service.category.AnimalCategoryStrategy;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class XmlFileParser implements FileParser {
    private final AnimalCategoryStrategy categoryStrategy;

    @Override
    public List<Animal> parse(List<String[]> animalsRecords) {
        List<Animal> animals = new ArrayList<>();
        for (String[] animalsRecord : animalsRecords) {
            try {
                Animal animal = getAnimalFromRecord(animalsRecord);
                Long categoryId = categoryStrategy
                        .getAnimalCategoryService(animal.getCost())
                        .getCategory(animal.getCost());
                animal.setCategoryId(categoryId);
                animals.add(animal);
            } catch (Exception e) {
                /**
                 * skip non-valid animals
                 * for example:
                 * Name starts with a lower-case letter
                 * Type is not of defined in Type enum
                 * Sex is not male of female
                 * weight or cost <= 0
                 */
            }
        }
        return animals;
    }

    @Override
    public boolean isApplicable(String fileType) {
        return fileType.equalsIgnoreCase(XML);
    }
}