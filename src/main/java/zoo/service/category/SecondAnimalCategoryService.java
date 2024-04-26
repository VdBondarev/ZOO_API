package zoo.service.category;

import static zoo.constants.NumbersConstantsHolder.SECOND_CATEGORY_RANGE_FROM;
import static zoo.constants.NumbersConstantsHolder.SECOND_CATEGORY_RANGE_TO;
import static zoo.constants.NumbersConstantsHolder.TWO;

import org.springframework.stereotype.Service;

@Service
public class SecondAnimalCategoryService implements AnimalCategoryService {

    @Override
    public Long getCategory(int cost) {
        return TWO;
    }

    @Override
    public boolean isApplicable(int cost) {
        return cost >= SECOND_CATEGORY_RANGE_FROM && cost <= SECOND_CATEGORY_RANGE_TO;
    }
}
