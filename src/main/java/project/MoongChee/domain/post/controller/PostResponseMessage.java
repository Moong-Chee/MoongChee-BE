package project.MoongChee.domain.post.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostResponseMessage {
    POST_CREATE_SUCCESS(201, "게시물 생성 성공"),
    POST_UPDATE_SUCCESS(200, "게시물 수정 성공"),
    POST_GETALL_SUCCESS(202, "게시물 전체 조회 성공");
    private final int code;
    private final String message;
}
