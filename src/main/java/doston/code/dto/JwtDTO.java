package doston.code.dto;

import lombok.Getter;
import lombok.Setter;


public record JwtDTO(
        String login,
        String role
) {

}
