package animals.model;

import static animals.constants.ConstantsHolder.ONE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@Table(name = "roles")
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    private RoleName name = RoleName.USER;

    public Role(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return name.name();
    }

    public enum RoleName {
        USER,
        ADMIN;

        public static RoleName fromString(String value) {
            boolean equalsSubstring;
            for (RoleName role : RoleName.values()) {
                equalsSubstring =
                        role.name()
                                .substring(role.name().indexOf("_") + ONE)
                                .equalsIgnoreCase(value);
                if (role.name().equalsIgnoreCase(value) || equalsSubstring) {
                    return role;
                }
            }
            throw new IllegalArgumentException("Unknown enum value: " + value);
        }

        @Override
        public String toString() {
            return name();
        }
    }
}
