package zoo.repository.specification.animal.provider;

import static zoo.constants.CriteriaQueryConstantsHolder.NAME_COLUMN;
import static zoo.constants.CriteriaQueryConstantsHolder.PERCENT;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import zoo.model.Animal;
import zoo.repository.specification.provider.LikeSpecificationProvider;

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
