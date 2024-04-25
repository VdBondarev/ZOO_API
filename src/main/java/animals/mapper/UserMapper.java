package animals.mapper;

import animals.config.MapperConfig;
import animals.dto.user.UserRegistrationRequestDto;
import animals.dto.user.UserResponseDto;
import animals.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User toModel(UserRegistrationRequestDto requestDto);

    UserResponseDto toResponseDto(User user);
}
