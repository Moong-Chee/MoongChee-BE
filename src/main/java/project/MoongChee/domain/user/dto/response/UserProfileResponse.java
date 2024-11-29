package project.MoongChee.domain.user.dto.response;

import java.time.LocalDate;
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

        LocalDate birthday,

        Department department,

        long studentNumber
) {
    public static UserProfileResponse from(User user, Department department, long studentNumber, LocalDate birthday) {
        return UserProfileResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .customId(user.getCustomId())
                .profileImageUrl(user.getProfileImage().getUrl())
                .phoneNumber(user.getPhoneNumber())
                .birthday(birthday)
                .department(user.getDepartment())
                .studentNumber(user.getStudentNumber())
                .build();
    }
}

