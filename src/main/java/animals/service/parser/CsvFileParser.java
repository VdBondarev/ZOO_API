package animals.service.parser;

import static animals.constants.ConstantsHolder.CSV;

import animals.model.Animal;
import animals.model.enums.Sex;
import animals.model.enums.Type;
import animals.service.category.AnimalCategoryStrategy;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CsvFileParser implements FileParser {
    private final AnimalCategoryStrategy categoryStrategy;

    @Override
    public List<Animal> parse(List<String[]> animalsRecords) {
        animalsRecords.remove(0); // remove the first record that contains description of a file
        List<Animal> animals = new ArrayList<>();
        for (String[] animalsRecord : animalsRecords) {
            try {
                animals.add(getAnimalFromRecord(animalsRecord));
            } catch (Exception e) {
                /**
                 * skip non-valid animals
                 * for example:
                 * Name starts with a lower-case letter
                 * Type is not of defined in Type enum
                 * Sex is not male of female
                 * weight or cost <= 0
                 */
                continue;
            }
        }
        return animals;
    }

    @Override
    public boolean isApplicable(String fileType) {
        return fileType.equalsIgnoreCase(CSV);
    }

    private Animal getAnimalFromRecord(String[] animalRecord) {
        String name = animalRecord[0];
        Type type = Type.fromString(animalRecord[1]);
        Sex sex = Sex.fromString(animalRecord[2]);
        int weight = Integer.parseInt(animalRecord[3]);
        int cost = Integer.parseInt(animalRecord[4]);
        Long categoryId = categoryStrategy.getAnimalCategoryService(cost).getCategory(cost);
        return new Animal()
                .setName(name)
                .setType(type)
                .setSex(sex)
                .setWeight(weight)
                .setCost(cost)
                .setCategoryId(categoryId);
    }
}
