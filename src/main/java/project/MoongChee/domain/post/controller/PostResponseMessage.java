package project.MoongChee.domain.post.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostResponseMessage {
    POST_CREATE_SUCCESS(200, "게시물 생성 성공"),
    POST_UPDATE_SUCCESS(200, "게시물 수정 성공"),
    POST_GETALL_SUCCESS(200, "게시물 전체 조회 성공"),
    POST_GETONE_SUCCESS(200, "게시물 하나 조회 성공"),
    POST_SEARCH_SUCCESS(200, "게시물 검색 성공"),
    POST_LIKE_SUCCESS(200, "게시물 관심 등록 성공"),
    POST_LIKE_REMOVE_SUCCESS(200, "게시물 관심 등록 삭제 성공"),
    POST_GETLIKE_SUCCESS(200, "관심 대여 게시물 조회 성공");
    private final int code;
    private final String message;
}
