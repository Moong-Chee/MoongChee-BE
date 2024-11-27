package project.MoongChee.global.auth.oauth.dto;

public record GoogleTokenResponse(
        String access_token,
        String expires_in,
        String scope,
        String token_type,
        String id_token
) {
}
