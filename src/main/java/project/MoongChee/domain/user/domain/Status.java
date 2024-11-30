package project.MoongChee.domain.user.domain;

public enum Status {
    WAITING, // 가입 대기
    ACTIVE,  // 가입 승인 완료
    BANNED,  // 정지된 사용자(추후 구현)
    LEFT     // 탈퇴한 사용자(추후 구현)
}
