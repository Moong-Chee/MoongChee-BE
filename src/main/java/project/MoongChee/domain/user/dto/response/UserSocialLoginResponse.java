package project.MoongChee.domain.user.dto.response;

import lombok.Builder;
import project.MoongChee.domain.user.domain.LoginStatus;
import project.MoongChee.global.auth.jwt.JwtResponse;

@Builder
public record UserSocialLoginResponse(
        Long id,
        LoginStatus status,
        String customId,
        JwtResponse jwtToken
) {
}
