package zoo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import zoo.config.MapperConfig;
import zoo.dto.user.UserRegistrationRequestDto;
import zoo.dto.user.UserResponseDto;
import zoo.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User toModel(UserRegistrationRequestDto requestDto);

    UserResponseDto toResponseDto(User user);
}
