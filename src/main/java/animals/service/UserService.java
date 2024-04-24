package animals.service;

import animals.dto.UserRegistrationRequestDto;
import animals.dto.UserRegistrationResponseDto;

public interface UserService {

    UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto);
}
