package zoo.repository.specification.animal.manager;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zoo.model.Animal;
import zoo.repository.specification.manager.LikeSpecificationProviderManager;
import zoo.repository.specification.provider.LikeSpecificationProvider;

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
