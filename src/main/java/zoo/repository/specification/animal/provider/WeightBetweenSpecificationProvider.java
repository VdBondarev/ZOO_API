package zoo.repository.specification.animal.provider;

import static zoo.constants.CriteriaQueryConstantsHolder.WEIGHT_COLUMN;
import static zoo.constants.NumbersConstantsHolder.ONE;
import static zoo.constants.NumbersConstantsHolder.TWO;
import static zoo.constants.NumbersConstantsHolder.ZERO;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import zoo.model.Animal;
import zoo.repository.specification.provider.BetweenSpecificationProvider;

@Component
public class WeightBetweenSpecificationProvider
        implements BetweenSpecificationProvider<Animal> {

    @Override
    public Specification<Animal> getSpecification(List<Integer> params) {
        Integer weightFrom;
        Integer weightTo;
        if (params.size() == ONE) {
            weightFrom = ZERO;
            weightTo = params.get(ZERO);
        } else if (params.size() == TWO) {
            weightFrom = params.get(ZERO);
            weightTo = params.get(ONE);
        } else {
            throw new IllegalArgumentException(
                    "Param weight should contain either 1 element, or 2. But was "
                            + params.size());
        }
        if (weightFrom.compareTo(weightTo) >= ZERO) {
            throw new IllegalArgumentException("Weight from should be bigger than weight to, "
                    + "but was " + params);
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(WEIGHT_COLUMN), weightFrom, weightTo);
    }

    @Override
    public String getKey() {
        return WEIGHT_COLUMN;
    }
}
