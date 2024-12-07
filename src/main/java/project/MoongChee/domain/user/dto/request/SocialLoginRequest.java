package project.MoongChee.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SocialLoginRequest(
        @NotBlank String authCode
) {
}
