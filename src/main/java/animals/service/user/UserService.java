package animals.service.user;

import animals.dto.user.UserRegistrationRequestDto;
import animals.dto.user.UserResponseDto;
import animals.model.User;

public interface UserService {

    UserResponseDto register(UserRegistrationRequestDto requestDto);

    UserResponseDto getMyInfo(User user);

    UserResponseDto updateUserRole(Long id, String roleName);
}
