package animals.repository.specification.animal.manager;

import animals.model.Animal;
import animals.repository.specification.manager.BetweenSpecificationProvideManager;
import animals.repository.specification.provider.BetweenSpecificationProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnimalBetweenSpecificationProvideManager
        implements BetweenSpecificationProvideManager<Animal> {
    private final List<BetweenSpecificationProvider<Animal>> specificationProviders;

    @Override
    public BetweenSpecificationProvider<Animal> getBetweenSpecificationProvider(String key) {
        return specificationProviders
                .stream()
                .filter(provider -> provider.getKey().equalsIgnoreCase(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Can't find specification provider for key " + key));
    }
}
