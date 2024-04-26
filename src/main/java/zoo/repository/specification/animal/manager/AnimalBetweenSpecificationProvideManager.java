package zoo.repository.specification.animal.manager;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zoo.model.Animal;
import zoo.repository.specification.manager.BetweenSpecificationProvideManager;
import zoo.repository.specification.provider.BetweenSpecificationProvider;

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
