package project.MoongChee.global.auth.jwt;

import lombok.Builder;

@Builder
public record JwtResponse(
        String accessToken,
        String refreshToken
) {
}
