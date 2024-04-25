package animals.service.parser;

import animals.model.Animal;
import java.util.List;

public interface FileParser {
    List<Animal> parse(List<String[]> animalsRecords);

    boolean isApplicable(String fileType);
}
