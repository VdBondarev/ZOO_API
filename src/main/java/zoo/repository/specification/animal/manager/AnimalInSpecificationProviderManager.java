package zoo.repository.specification.animal.manager;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zoo.model.Animal;
import zoo.repository.specification.manager.InSpecificationProviderManager;
import zoo.repository.specification.provider.InSpecificationProvider;

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
