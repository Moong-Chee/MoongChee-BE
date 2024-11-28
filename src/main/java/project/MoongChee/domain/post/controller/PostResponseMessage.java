package project.MoongChee.domain.post.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostResponseMessage {
    POST_CREATE_SUCCESS(201, "게시물 생성 성공");
    private final int code;
    private final String message;
}
