package project.MoongChee.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record UserInitializeRequest(
        @NotNull LocalDate birth,
        @NotBlank String customId
) {
}
