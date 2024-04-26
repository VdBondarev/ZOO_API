package animals.security;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import animals.dto.user.UserLoginRequestDto;
import animals.dto.user.UserLoginResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("Verify that login() method work fine with valid input")
    void login_ValidRequest_ReturnsValidToken() {
        UserLoginRequestDto requestDto = new UserLoginRequestDto(
                "test@example.com",
                "password");

        // Mock authentication result
        // Mock JWT token generation
        Authentication authentication = mock(Authentication.class);
        String token = "super_secret_token125u23y65021360fsudit`g783w65";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtil.generateToken(authentication.getName())).thenReturn(token);

        // Execute the method
        UserLoginResponseDto responseDto = authenticationService.login(requestDto);

        // Verify that authenticationManager.authenticate and jwtUtil.generateToken were called
        verify(authenticationManager, times(1))
                .authenticate(new UsernamePasswordAuthenticationToken(
                        requestDto.email(), requestDto.password())
                );
        verify(jwtUtil, times(1)).generateToken(authentication.getName());

        assertNotNull(responseDto);
        assertEquals(token, responseDto.token());
    }

    @Test
    @DisplayName("Verify that login() method works as expected with non-valid input")
    void login_InvalidInput_ThrowsException() {
        UserLoginRequestDto requestDto = new UserLoginRequestDto(
                null,
                null); // Provide invalid input

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        assertThrows(BadCredentialsException.class, () -> authenticationService.login(requestDto));

        // Verify that authenticationManager.authenticate was called
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Ensure jwtUtil.generateToken is not called
        verify(jwtUtil, never()).generateToken(any());
    }
}
