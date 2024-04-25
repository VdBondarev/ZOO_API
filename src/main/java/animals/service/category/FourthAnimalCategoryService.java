package animals.service.category;

import static animals.constants.ConstantsHolder.FOUR;
import static animals.constants.ConstantsHolder.FOURTH_CATEGORY_RANGE;

import org.springframework.stereotype.Service;

@Service
public class FourthAnimalCategoryService implements AnimalCategoryService {

    @Override
    public Long getCategory(int cost) {
        return FOUR;
    }

    @Override
    public boolean isApplicable(int cost) {
        return cost >= FOURTH_CATEGORY_RANGE;
    }
}
