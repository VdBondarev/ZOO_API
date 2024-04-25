package animals.repository.specification.animal;

import static animals.constants.OtherConstantsHolder.CATEGORY_ID_COLUMN;
import static animals.constants.OtherConstantsHolder.COST_COLUMN;
import static animals.constants.OtherConstantsHolder.NAME_COLUMN;
import static animals.constants.OtherConstantsHolder.SEX_COLUMN;
import static animals.constants.OtherConstantsHolder.TYPE_COLUMN;
import static animals.constants.OtherConstantsHolder.WEIGHT_COLUMN;

import animals.dto.animal.AnimalSearchParamsRequestDto;
import animals.model.Animal;
import animals.repository.specification.SpecificationBuilder;
import animals.repository.specification.manager.BetweenSpecificationProvideManager;
import animals.repository.specification.manager.InSpecificationProviderManager;
import animals.repository.specification.manager.LikeSpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnimalSpecificationBuilder
        implements SpecificationBuilder<Animal, AnimalSearchParamsRequestDto> {
    private final BetweenSpecificationProvideManager<Animal>
            betweenSpecificationProvideManager;
    private final LikeSpecificationProviderManager<Animal>
            likeSpecificationProviderManager;
    private final InSpecificationProviderManager<Animal, List<Long>>
            inSpecificationProviderManager;

    @Override
    public Specification<Animal> build(AnimalSearchParamsRequestDto paramsDto) {
        Specification<Animal> specification = Specification.where(null);
        if (notEmpty(paramsDto.name())) {
            specification = getLikeSpecification(
                    NAME_COLUMN,
                    specification,
                    paramsDto.name());
        }
        if (notEmpty(paramsDto.sex())) {
            specification = getLikeSpecification(
                    SEX_COLUMN,
                    specification,
                    paramsDto.sex());
        }
        if (notEmpty(paramsDto.type())) {
            specification = getLikeSpecification(
                    TYPE_COLUMN,
                    specification,
                    paramsDto.type());
        }
        if (notEmpty(paramsDto.cost())) {
            specification = getBetweenSpecification(
                    COST_COLUMN,
                    specification,
                    paramsDto.cost());
        }
        if (notEmpty(paramsDto.weight())) {
            specification = getBetweenSpecification(
                    WEIGHT_COLUMN,
                    specification,
                    paramsDto.weight());
        }
        if (paramsDto.categoriesIds() != null && !paramsDto.categoriesIds().isEmpty()) {
            specification = specification.and(
                    inSpecificationProviderManager
                            .getInSpecificationProvider(CATEGORY_ID_COLUMN)
                            .getSpecification(paramsDto.categoriesIds())
            );
        }
        return specification;
    }

    private boolean notEmpty(String param) {
        return param != null && !param.isEmpty();
    }

    private boolean notEmpty(List<Integer> params) {
        return params != null && !params.isEmpty();
    }

    private Specification<Animal> getLikeSpecification(
            String column,
            Specification<Animal> specification,
            String param) {
        return specification.and(
                likeSpecificationProviderManager
                        .getLikeSpecificationProvider(column)
                        .getSpecification(param)
        );
    }

    private Specification<Animal> getBetweenSpecification(
            String column,
            Specification<Animal> specification,
            List<Integer> params) {
        return specification.and(
                betweenSpecificationProvideManager
                        .getBetweenSpecificationProvider(column)
                        .getSpecification(params)
        );
    }
}
