package animals.repository.specification.animal.provider;

import static animals.constants.CriteriaQueryConstantsHolder.CATEGORY_ID_COLUMN;

import animals.model.Animal;
import animals.repository.specification.provider.InSpecificationProvider;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CategoriesInSpecificationProvider
        implements InSpecificationProvider<Animal, List<Long>> {

    @Override
    public Specification<Animal> getSpecification(List<Long> searchParams) {
        return (root, query, criteriaBuilder) -> root.get(CATEGORY_ID_COLUMN).in(searchParams);
    }

    @Override
    public String getKey() {
        return CATEGORY_ID_COLUMN;
    }
}
