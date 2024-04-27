package zoo.service.parser;

import java.util.List;
import zoo.model.Animal;
import zoo.model.enums.Sex;
import zoo.model.enums.Type;

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
