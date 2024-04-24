package animals.dto;

import animals.annotation.FieldMatch;
import animals.annotation.StartsWithCapital;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@FieldMatch(password = "password", repeatPassword = "repeatPassword")
public record UserRegistrationRequestDto(
        @Email
        @NotBlank
        String email,
        @NotBlank
        @StartsWithCapital
        String firstName,
        @NotBlank
        @StartsWithCapital
        String lastName,
        @NotBlank
        @Length(min = 8, max = 35)
        String password,
        @NotBlank
        @Length(min = 8, max = 35)
        String repeatPassword
) {
}
