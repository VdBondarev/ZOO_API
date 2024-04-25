package animals.repository.specification.animal.provider;

import static animals.constants.CriteriaQueryConstantsHolder.NAME_COLUMN;
import static animals.constants.CriteriaQueryConstantsHolder.PERCENT;

import animals.model.Animal;
import animals.repository.specification.provider.LikeSpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class NameLikeSpecificationProvider
        implements LikeSpecificationProvider<Animal> {

    @Override
    public Specification<Animal> getSpecification(String param) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                root.get(NAME_COLUMN), PERCENT + param + PERCENT
        );
    }

    @Override
    public String getKey() {
        return NAME_COLUMN;
    }
}
