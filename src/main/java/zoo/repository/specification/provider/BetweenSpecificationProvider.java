package zoo.repository.specification.provider;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public interface BetweenSpecificationProvider<T> {

    Specification<T> getSpecification(List<Integer> params);

    String getKey();
}
