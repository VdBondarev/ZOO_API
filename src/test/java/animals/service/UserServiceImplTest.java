package animals.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import animals.dto.user.UserRegistrationRequestDto;
import animals.dto.user.UserResponseDto;
import animals.mapper.UserMapper;
import animals.model.Role;
import animals.model.User;
import animals.model.enums.RoleName;
import animals.repository.UserRepository;
import animals.service.user.UserServiceImpl;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Verify that registration works fine for valid input params")
    void register_ValidRequest_RegistersUser() {
        UserRegistrationRequestDto requestDto =
                createRegistrationRequestDto("test@gmail.com", "testPassword");

        User user = createUser(requestDto);

        UserResponseDto expected = createResponseDto(user);

        when(userRepository.findByEmailWithoutRoles(requestDto.email()))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode(requestDto.password())).thenReturn(requestDto.password());
        when(userMapper.toModel(requestDto)).thenReturn(user);
        when(userMapper.toResponseDto(user)).thenReturn(expected);

        UserResponseDto actual = userService.register(requestDto);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify that registration works as expected for already registered email")
    void register_AlreadyRegisteredEmail_ThrowsException() {
        UserRegistrationRequestDto requestDto =
                createRegistrationRequestDto("test@gmail.com", "testPassword");

        User user = createUser(requestDto);

        when(userRepository.findByEmailWithoutRoles(requestDto.email()))
                .thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.register(requestDto));

        String expected = """
                User with passed email already registered
                Try another one
                """;
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify that updateUserRole() works fine when updating user to user")
    void updateUserRole_AlreadyCustomer_ReturnsNothingUpdated() {
        User user = createUser();

        UserResponseDto expected = createResponseDto(user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toResponseDto(user)).thenReturn(expected);

        UserResponseDto actual =
                userService.updateUserRole(user.getId(), "USER");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify that updateUserRole() works fine when updating admin to user")
    void updateUserRole_UpdateManagerToCustomer_ReturnsUpdatedUser() {
        Role userRole = new Role(1L);
        userRole.setName(RoleName.USER);

        Role admin = new Role(2L);
        admin.setName(RoleName.ADMIN);

        // now this user is an admin
        User user = createUser();
        user.setRoles(Set.of(userRole, admin));

        UserResponseDto expected = createResponseDto(user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponseDto(user)).thenReturn(expected);

        UserResponseDto actual =
                userService.updateUserRole(user.getId(), "USER");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify that updateUserRole() works fine when updating user to admin")
    void updateUserRole_UpdateCustomerToManager_ReturnsUpdatedUser() {
        User user = createUser();

        // expecting that user will be admin after updating
        UserResponseDto expected = createResponseDto(user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toResponseDto(user)).thenReturn(expected);

        UserResponseDto actual =
                userService.updateUserRole(user.getId(), "ADMIN");

        assertEquals(expected, actual);
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("testemail@example.com");
        user.setPassword("testpassword");
        user.setLastName("Test");
        user.setFirstName("Test");
        return user;
    }

    private User createUser(UserRegistrationRequestDto requestDto) {
        return new User()
                .setId(1L)
                .setPassword(requestDto.password())
                .setRoles(Set.of(new Role(1L)))
                .setEmail(requestDto.email())
                .setLastName(requestDto.lastName())
                .setFirstName(requestDto.firstName());
    }

    private UserResponseDto createResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail());
    }

    private UserRegistrationRequestDto createRegistrationRequestDto(String email, String password) {
        return new UserRegistrationRequestDto(
                "Firstname",
                "LastName",
                email,
                password,
                password);
    }
}
