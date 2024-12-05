package project.MoongChee.domain.review.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewErrorMessage {
    DUPLICATE_REVIEW(400, "이미 리뷰를 작성했습니다.");

    private final int code;
    private final String message;
}
