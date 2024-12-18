package doston.code.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private String login;
    private String role;

    public JwtDTO() {
    }

    public JwtDTO(String login, String role) {
        this.login = login;
        this.role = role;
    }
}
