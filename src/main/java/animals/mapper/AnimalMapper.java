package animals.mapper;

import animals.config.MapperConfig;
import animals.dto.animal.AnimalResponseDto;
import animals.model.Animal;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface AnimalMapper {
    AnimalResponseDto toResponseDto(Animal animal);
}
