package project.MoongChee.domain.post.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostErrorMessage {
    POST_NOT_FOUND(404, "없는 대여 게시물입니다."),
    POST_UNAUTHORIZED(403, "수정 권한이 없습니다."),
    NAME_NOT_FOUND(404, "해당 이름을 가진 대여 게시물이 없습니다."),
    KEYWORD_NOT_FOUND(404, "해당 키워드를 가진 대여 게시물이 없습니다.");

    private final int code;
    private final String message;
}
