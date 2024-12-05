package project.MoongChee.domain.review.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewResponseMessage {
    REVIEW_CREATE_SUCCESS(201, "리뷰 작성 성공"),
    REVIEW_GET_SUCCESS(200, "리뷰 조회 성공");
    private final int code;
    private final String message;
}
