package project.MoongChee.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import project.MoongChee.domain.user.domain.Department;

public record UserInitializeRequest(
        @NotBlank String customId,
        @NotNull String phoneNumber,
        @NotNull LocalDate birthday,
        @NotNull long studentNumber,
        @NotNull Department department
) {
}
