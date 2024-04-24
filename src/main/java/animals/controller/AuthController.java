package animals.controller;

import animals.dto.UserLoginRequestDto;
import animals.dto.UserLoginResponseDto;
import animals.dto.UserRegistrationRequestDto;
import animals.dto.UserResponseDto;
import animals.security.AuthenticationService;
import animals.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @GetMapping("/login")
    @Operation(summary = "Login for signed-up users only")
    public UserLoginResponseDto login(
            @RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.login(requestDto);
    }

    @PostMapping("/register")
    @Operation(summary = "Registration for any user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto requestDto) {
        return userService.register(requestDto);
    }
}
