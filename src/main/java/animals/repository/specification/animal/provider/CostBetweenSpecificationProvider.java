package animals.repository.specification.animal.provider;

import static animals.constants.CriteriaQueryConstantsHolder.COST_COLUMN;
import static animals.constants.NumbersConstantsHolder.ONE;
import static animals.constants.NumbersConstantsHolder.TWO;
import static animals.constants.NumbersConstantsHolder.ZERO;

import animals.model.Animal;
import animals.repository.specification.provider.BetweenSpecificationProvider;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CostBetweenSpecificationProvider
        implements BetweenSpecificationProvider<Animal> {

    @Override
    public Specification<Animal> getSpecification(List<Integer> params) {
        Integer costFrom;
        Integer costTo;
        if (params.size() == ONE) {
            costFrom = ZERO;
            costTo = params.get(ZERO);
        } else if (params.size() == TWO) {
            costFrom = params.get(ZERO);
            costTo = params.get(ONE);
        } else {
            throw new IllegalArgumentException(
                    "Param cost should contain either 1 element, or 2. But was "
                            + params.size());
        }
        if (costFrom.compareTo(costTo) >= ZERO) {
            throw new IllegalArgumentException("Cost from should be bigger than cost to, "
                    + "but was " + params);
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(COST_COLUMN), costFrom, costTo);
    }

    @Override
    public String getKey() {
        return COST_COLUMN;
    }
}
