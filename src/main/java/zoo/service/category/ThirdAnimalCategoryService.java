package zoo.service.category;

import static zoo.constants.NumbersConstantsHolder.THIRD_CATEGORY_RANGE_FROM;
import static zoo.constants.NumbersConstantsHolder.THIRD_CATEGORY_RANGE_TO;
import static zoo.constants.NumbersConstantsHolder.THREE;

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
