package animals.service.category;

import static animals.constants.NumbersConstantsHolder.THIRD_CATEGORY_RANGE_FROM;
import static animals.constants.NumbersConstantsHolder.THIRD_CATEGORY_RANGE_TO;
import static animals.constants.NumbersConstantsHolder.THREE;

import org.springframework.stereotype.Service;

@Service
public class ThirdAnimalCategoryService implements AnimalCategoryService {

    @Override
    public Long getCategory(int cost) {
        return THREE;
    }

    @Override
    public boolean isApplicable(int cost) {
        return cost >= THIRD_CATEGORY_RANGE_FROM && cost <= THIRD_CATEGORY_RANGE_TO;
    }
}
