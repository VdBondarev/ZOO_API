package animals.mapper;

import animals.config.MapperConfig;
import animals.dto.UserRegistrationRequestDto;
import animals.dto.UserRegistrationResponseDto;
import animals.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User toModel(UserRegistrationRequestDto requestDto);

    UserRegistrationResponseDto toResponseDto(User user);
}
