package animals.service.category;

import static animals.constants.NumbersConstantsHolder.FIRST_CATEGORY_RANGE;
import static animals.constants.NumbersConstantsHolder.ONE;

import org.springframework.stereotype.Service;

@Service
public class FirstAnimalCategoryService implements AnimalCategoryService {

    @Override
    public Long getCategory(int cost) {
        return (long) ONE;
    }

    @Override
    public boolean isApplicable(int cost) {
        return cost <= FIRST_CATEGORY_RANGE;
    }
}
