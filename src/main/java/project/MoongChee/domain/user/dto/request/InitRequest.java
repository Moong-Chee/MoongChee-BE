package project.MoongChee.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import project.MoongChee.domain.user.domain.Department;

public record InitRequest(
        @NotNull String phoneNumber,
        @NotNull LocalDate birthday,
        @NotNull long studentNumber,
        @NotNull Department department
) {
}
