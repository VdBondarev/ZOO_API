package animals.repository.specification.manager;

import animals.repository.specification.provider.InSpecificationProvider;

public interface InSpecificationProviderManager<T, P> {

    InSpecificationProvider<T, P> getInSpecificationProvider(String key);
}
