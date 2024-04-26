package zoo.repository.specification.provider;

import org.springframework.data.jpa.domain.Specification;

public interface InSpecificationProvider<T, P> {

    Specification<T> getSpecification(P searchParams);

    String getKey();
}
