package animals.service.category;

import org.springframework.stereotype.Service;

@Service
public class ThirdAnimalCategoryService implements AnimalCategoryService {

    @Override
    public Long getCategory(int cost) {
        return 3L;
    }

    @Override
    public boolean isApplicable(int cost) {
        return cost >= 41 && cost <= 60;
    }
}
