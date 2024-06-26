package zoo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zoo.dto.user.UserResponseDto;
import zoo.model.User;
import zoo.service.user.UserService;

@Tag(name = "Users controller",
        description = "Endpoints for managing users")
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
            description = "Allowed for admins only")
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
