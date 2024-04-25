package animals.repository.specification.animal.manager;

import animals.model.Animal;
import animals.repository.specification.manager.InSpecificationProviderManager;
import animals.repository.specification.provider.InSpecificationProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnimalInSpecificationProviderManager
        implements InSpecificationProviderManager<Animal, List<Long>> {
    private final List<InSpecificationProvider<Animal, List<Long>>> specificationProviders;

    @Override
    public InSpecificationProvider<Animal, List<Long>> getInSpecificationProvider(String key) {
        return specificationProviders
                .stream()
                .filter(provider -> provider.getKey().equalsIgnoreCase(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Can't find specification provider for key " + key));
    }
}
