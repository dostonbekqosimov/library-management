package doston.code.dto.request;

import jakarta.validation.constraints.Size;

import java.util.List;

public record BookFilterDTO(
        AvailabilityStatus availability,

        List<Long> genreIds,

        String authorName
) {
    public enum AvailabilityStatus {
        ALL,
        IN_STOCK,
        OUT_OF_STOCK
    }
}

