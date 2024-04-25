package animals.repository.specification.animal.manager;

import animals.model.Animal;
import animals.repository.specification.manager.LikeSpecificationProviderManager;
import animals.repository.specification.provider.LikeSpecificationProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnimalLikeSpecificationProviderManager
        implements LikeSpecificationProviderManager<Animal> {
    private final List<LikeSpecificationProvider<Animal>> specificationProviders;

    @Override
    public LikeSpecificationProvider<Animal> getLikeSpecificationProvider(String key) {
        return specificationProviders
                .stream()
                .filter(provider -> provider.getKey().equalsIgnoreCase(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Can't find specification provider for key " + key));
    }
}
