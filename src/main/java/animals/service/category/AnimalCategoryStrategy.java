package animals.service.category;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnimalCategoryStrategy {
    private final List<AnimalCategoryService> categoryServices;

    public AnimalCategoryService getAnimalCategoryService(int cost) {
        return categoryServices
                .stream()
                .filter(service -> service.isApplicable(cost))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find category for cost " + cost));
    }
}
