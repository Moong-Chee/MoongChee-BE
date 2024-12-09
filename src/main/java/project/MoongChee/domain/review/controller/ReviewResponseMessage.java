package project.MoongChee.domain.review.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewResponseMessage {
    REVIEW_CREATE_SUCCESS(200, "리뷰 작성 성공"),
    MY_REVIEW_GET_SUCCESS(200, "자신의 리뷰 조회 성공"),
    OTHER_REVIEW_GET_SUCCESS(200, "특정 사용자 리뷰 조회 성공");
    private final int code;
    private final String message;
}
