package zoo.mapper;

import org.mapstruct.Mapper;
import zoo.config.MapperConfig;
import zoo.dto.animal.AnimalResponseDto;
import zoo.model.Animal;

@Mapper(config = MapperConfig.class)
public interface AnimalMapper {

    AnimalResponseDto toResponseDto(Animal animal);
}
