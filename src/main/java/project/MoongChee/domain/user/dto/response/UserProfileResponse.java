package project.MoongChee.domain.user.dto.response;

import lombok.Builder;
import project.MoongChee.domain.user.domain.Department;
import project.MoongChee.domain.user.domain.User;

@Builder
public record UserProfileResponse(
        String name,
        String email,
        String profileImageUrl,
        Department department
) {
    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImage().getUrl())
                .department(user.getDepartment())
                .build();
    }
}
