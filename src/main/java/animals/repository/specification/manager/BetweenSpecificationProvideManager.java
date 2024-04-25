package animals.repository.specification.manager;

import animals.repository.specification.provider.BetweenSpecificationProvider;

public interface BetweenSpecificationProvideManager<T> {

    BetweenSpecificationProvider<T> getBetweenSpecificationProvider(String key);
}
