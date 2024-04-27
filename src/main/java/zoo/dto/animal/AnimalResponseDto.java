package zoo.dto.animal;

import zoo.model.enums.Sex;
import zoo.model.enums.Type;

public record AnimalResponseDto(
        Long id,
        String name,
        Type type,
        Sex sex,
        int weight,
        int cost,
        Long categoryId
) {
}
