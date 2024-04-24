package animals.service;

import animals.dto.UserRegistrationRequestDto;
import animals.dto.UserRegistrationResponseDto;
import animals.mapper.UserMapper;
import animals.model.User;
import animals.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto) {
        userRepository.findByEmailWithoutRoles(requestDto.email()).ifPresent(user -> {
            throw new IllegalArgumentException("""
                    User with passed email already registered
                    Try another one
                    """);
        });
        String encodedPassword = passwordEncoder.encode(requestDto.password());
        User user = userMapper.toModel(requestDto)
                .setPassword(encodedPassword);
        userRepository.save(user);
        return userMapper.toResponseDto(user);
    }
}
