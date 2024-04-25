package animals.service.category;

import org.springframework.stereotype.Service;

@Service
public class FirstAnimalCategoryService implements AnimalCategoryService {

    @Override
    public Long getCategory(int cost) {
        return 0L;
    }

    @Override
    public boolean isApplicable(int cost) {
        return cost <= 20;
    }
}
