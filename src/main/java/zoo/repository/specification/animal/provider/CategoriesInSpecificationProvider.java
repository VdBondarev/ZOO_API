package zoo.repository.specification.animal.provider;

import static zoo.constants.CriteriaQueryConstantsHolder.CATEGORY_ID_COLUMN;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import zoo.model.Animal;
import zoo.repository.specification.provider.InSpecificationProvider;

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
