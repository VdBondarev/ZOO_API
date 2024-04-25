package animals.repository.specification.animal.provider;

import static animals.constants.CriteriaQueryConstantsHolder.PERCENT;
import static animals.constants.CriteriaQueryConstantsHolder.SEX_COLUMN;

import animals.model.Animal;
import animals.repository.specification.provider.LikeSpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

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
