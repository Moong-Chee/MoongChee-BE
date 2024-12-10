package project.MoongChee.domain.post.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostResponseMessage {
    POST_CREATE_SUCCESS(200, "게시물 생성 성공"),
    POST_UPDATE_SUCCESS(200, "게시물 수정 성공"),
    POST_GET_ALL_SUCCESS(200, "게시물 전체 조회 성공"),
    POST_GET_ONE_SUCCESS(200, "게시물 하나 조회 성공"),
    POST_SEARCH_SUCCESS(200, "게시물 검색 성공"),
    POST_LIKE_SUCCESS(200, "게시물 관심 등록 성공"),
    POST_LIKE_REMOVE_SUCCESS(200, "게시물 관심 등록 삭제 성공"),
    POST_GET_LIKE_SUCCESS(200, "관심 게시물 조회 성공"),
    POST_MY_ACTIVE_SUCCESS(200, "나의 진행중인 거래 조회 성공"),
    POST_MY_CLOSED_SUCCESS(200, "나의 종료된 거래 조회 성공");
    private final int code;
    private final String message;
}
