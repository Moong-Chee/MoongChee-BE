package project.MoongChee.domain.user.dto.response;

import lombok.Builder;
import project.MoongChee.domain.user.domain.LoginStatus;
import project.MoongChee.global.auth.jwt.JwtResponse;

@Builder
public record SocialLoginResponse(
        Long id,
        LoginStatus status,
        JwtResponse jwtToken
) {
}
