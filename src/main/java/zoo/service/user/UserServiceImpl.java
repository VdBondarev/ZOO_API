package zoo.service.user;

import static zoo.constants.NumbersConstantsHolder.ONE;
import static zoo.constants.OtherConstantsHolder.ROLE_ADMIN;
import static zoo.constants.OtherConstantsHolder.ROLE_USER;

import jakarta.persistence.EntityNotFoundException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zoo.dto.user.UserRegistrationRequestDto;
import zoo.dto.user.UserResponseDto;
import zoo.mapper.UserMapper;
import zoo.model.Role;
import zoo.model.User;
import zoo.model.enums.RoleName;
import zoo.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
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

    @Override
    public UserResponseDto getMyInfo(User user) {
        return userMapper.toResponseDto(user);
    }

    @Override
    public UserResponseDto updateUserRole(Long id, String roleName) {
        RoleName.fromString(roleName); // just a check if role exists
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "User with passed id doesn't exist, id: " + id));
        if (alreadyIs(user, roleName)) {
            return userMapper.toResponseDto(user);
        }
        if (hasRole(user, ROLE_ADMIN)
                && roleName.equalsIgnoreCase(ROLE_USER)) {
            user.setRoles(Set.of(new Role(1L)));
        } else {
            user.setRoles(Set.of(new Role(1L), new Role(2L)));
        }
        userRepository.save(user);
        return userMapper.toResponseDto(user);
    }

    private boolean alreadyIs(User user, String roleName) {
        return (isJustUser(user)
                && roleName.equalsIgnoreCase(ROLE_USER))
                || (hasRole(user, ROLE_ADMIN)
                && roleName.equalsIgnoreCase(ROLE_ADMIN));
    }

    private boolean isJustUser(User user) {
        return user.getRoles().size() == ONE;
    }

    private boolean hasRole(User user, String roleName) {
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .toList()
                .contains(RoleName.fromString(roleName));
    }
}
