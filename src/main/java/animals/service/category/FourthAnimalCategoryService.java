package animals.service.category;

import org.springframework.stereotype.Service;

@Service
public class FourthAnimalCategoryService implements AnimalCategoryService {

    @Override
    public Long getCategory(int cost) {
        return 4L;
    }

    @Override
    public boolean isApplicable(int cost) {
        return cost >= 61;
    }
}
