package zoo.repository.specification.animal.provider;

import static zoo.constants.CriteriaQueryConstantsHolder.PERCENT;
import static zoo.constants.CriteriaQueryConstantsHolder.SEX_COLUMN;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import zoo.model.Animal;
import zoo.repository.specification.provider.LikeSpecificationProvider;

@Component
public class SexLikeSpecificationProvider
        implements LikeSpecificationProvider<Animal> {

    @Override
    public Specification<Animal> getSpecification(String param) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(
                root.get(SEX_COLUMN), param + PERCENT
        ));
    }

    @Override
    public String getKey() {
        return SEX_COLUMN;
    }
}
