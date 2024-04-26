package zoo.repository.specification.manager;

import zoo.repository.specification.provider.InSpecificationProvider;

public interface InSpecificationProviderManager<T, P> {

    InSpecificationProvider<T, P> getInSpecificationProvider(String key);
}
