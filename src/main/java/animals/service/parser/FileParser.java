package animals.service.parser;

import animals.model.Animal;
import animals.model.enums.Sex;
import animals.model.enums.Type;
import java.util.List;

public interface FileParser {
    List<Animal> parse(List<String[]> animalsRecords);

    boolean isApplicable(String fileType);

    default Animal getAnimalFromRecord(String[] animalRecord) {
        String name = animalRecord[0];
        Type type = Type.fromString(animalRecord[1]);
        Sex sex = Sex.fromString(animalRecord[2]);
        int weight = Integer.parseInt(animalRecord[3]);
        int cost = Integer.parseInt(animalRecord[4]);
        return new Animal()
                .setName(name)
                .setType(type)
                .setSex(sex)
                .setWeight(weight)
                .setCost(cost);
    }
}
