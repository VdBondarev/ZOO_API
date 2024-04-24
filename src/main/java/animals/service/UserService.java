package animals.service;

import animals.dto.UserRegistrationRequestDto;
import animals.dto.UserResponseDto;
import animals.model.User;

public interface UserService {

    UserResponseDto register(UserRegistrationRequestDto requestDto);

    UserResponseDto getMyInfo(User user);

    UserResponseDto updateUserRole(Long id, String roleName);
}
