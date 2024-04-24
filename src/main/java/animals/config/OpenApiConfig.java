package animals.config;

import static animals.constants.ConstantsHolder.BEARER;
import static animals.constants.ConstantsHolder.BEARER_AUTH;
import static animals.constants.ConstantsHolder.JWT;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
          .components(new Components().addSecuritySchemes(BEARER_AUTH,
                new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme(BEARER)
                .bearerFormat(JWT)))
                .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH));
    }
}
