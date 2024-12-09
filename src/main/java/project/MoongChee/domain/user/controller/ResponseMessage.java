package project.MoongChee.domain.user.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    // 로그인 회원가입 관련
    REGISTER_SUCCESS(201, "회원가입에 성공했습니다."),
    LOGIN_SUCCESS(200, "로그인에 성공했습니다."),
    // 프로필 관련
    INIT_PROFILE_SUCCESS(200, "프로필 초기설정에 성공했습니다."),
    MY_PROFILE_SUCCESS(200, "나의 기본 프로필 조회에 성공했습니다."),
    USER_PROFILE_SUCCESS(200, "상대방 기본 프로필 조회에 성공했습니다."),
    // 어드민 관련
    USER_ACCEPT_SUCCESS(200, "유저 가입 승인에 성공했습니다."),
    //마이페이지 관련
    MY_PAGE_SUCCESS(200, "마이 페이지 조회에 성공했습니다.");

    private final int code;
    private final String message;

}
