package animals.dto.animal;

import animals.model.enums.Sex;
import animals.model.enums.Type;

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
