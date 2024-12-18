package doston.code.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;



@JsonInclude(JsonInclude.Include.NON_NULL)
public record JwtResponseDTO(
        String token,
        String type,
        String refreshToken,
        String username,
        List<String> roles
) {
    // Default constructor to set the type field to "Bearer" as it's a constant
    public JwtResponseDTO {
        if (type == null) {
            type = "Bearer";
        }
    }
}
