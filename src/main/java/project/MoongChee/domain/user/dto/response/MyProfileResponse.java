package project.MoongChee.domain.user.dto.response;

import java.time.LocalDate;
import lombok.Builder;
import project.MoongChee.domain.user.domain.Department;
import project.MoongChee.domain.user.domain.User;

@Builder
public record MyProfileResponse(
        String name,
        String email,
        String profileImageUrl,

        String phoneNumber,

        LocalDate birthday,

        Department department,

        long studentNumber
) {
    public static MyProfileResponse from(User user, Department department, long studentNumber, LocalDate birthday) {
        return MyProfileResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImage().getUrl())
                .phoneNumber(user.getPhoneNumber())
                .birthday(birthday)
                .department(user.getDepartment())
                .studentNumber(user.getStudentNumber())
                .build();
    }
}

