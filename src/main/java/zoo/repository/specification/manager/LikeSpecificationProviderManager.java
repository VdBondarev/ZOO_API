package zoo.repository.specification.manager;

import zoo.repository.specification.provider.LikeSpecificationProvider;

public interface LikeSpecificationProviderManager<T> {

    LikeSpecificationProvider<T> getLikeSpecificationProvider(String key);
}
