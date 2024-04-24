package animals.controller;

import animals.dto.UserResponseDto;
import animals.model.User;
import animals.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get your profile's info")
    @GetMapping
    public UserResponseDto getMyInfo(Authentication authentication) {
        return userService.getMyInfo(getUser(authentication));
    }

    @Operation(summary = "Update user's roles",
            description = """
                    Endpoint for updating the user's role.
                    Allowed for admins only
                    """)
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponseDto updateUserRole(
            @PathVariable Long id,
            @RequestParam(name = "role_name") String roleName) {
        return userService.updateUserRole(id, roleName);
    }

    private User getUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}
