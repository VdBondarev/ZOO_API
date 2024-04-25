package animals.repository.specification.manager;

import animals.repository.specification.provider.LikeSpecificationProvider;

public interface LikeSpecificationProviderManager<T> {

    LikeSpecificationProvider<T> getLikeSpecificationProvider(String key);
}
