package zoo.repository.specification.manager;

import zoo.repository.specification.provider.BetweenSpecificationProvider;

public interface BetweenSpecificationProvideManager<T> {

    BetweenSpecificationProvider<T> getBetweenSpecificationProvider(String key);
}
