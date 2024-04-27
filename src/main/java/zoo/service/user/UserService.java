package zoo.service.user;

import zoo.dto.user.UserRegistrationRequestDto;
import zoo.dto.user.UserResponseDto;
import zoo.model.User;

public interface UserService {

    UserResponseDto register(UserRegistrationRequestDto requestDto);

    UserResponseDto getMyInfo(User user);

    UserResponseDto updateUserRole(Long id, String roleName);
}
