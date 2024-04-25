package animals.service.category;

import org.springframework.stereotype.Service;

@Service
public class SecondAnimalCategoryService implements AnimalCategoryService {

    @Override
    public Long getCategory(int cost) {
        return 1L;
    }

    @Override
    public boolean isApplicable(int cost) {
        return cost >= 21 && cost <= 40;
    }
}
