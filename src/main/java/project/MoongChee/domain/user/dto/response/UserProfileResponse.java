package project.MoongChee.domain.user.dto.response;

import lombok.Builder;
import project.MoongChee.domain.user.domain.Department;
import project.MoongChee.domain.user.domain.User;

@Builder
public record UserProfileResponse(
        Long userId,
        String name,
        String customId,
        String profileImageUrl,

        String phoneNumber,

        String birthday,

        Department department,

        long studentNumber
) {
    public static UserProfileResponse from(User user, Department department, long studentNumber, String birthday) {
        return UserProfileResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .customId(user.getCustomId())
                .profileImageUrl(user.getProfileImageUrl())
                .phoneNumber(user.getPhoneNumber())
                .birthday(user.getBirthday())
                .department(user.getDepartment())
                .studentNumber(user.getStudentNumber())
                .build();
    }
}

