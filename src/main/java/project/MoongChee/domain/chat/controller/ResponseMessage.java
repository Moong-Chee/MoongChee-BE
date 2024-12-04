package project.MoongChee.domain.chat.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    CHATROOM_CREATE_SUCCESS(201, "채팅방 생성에 성공했습니다."),
    GET_CHATROOM(200, "하나의 채팅방을 반환합니다."),
    GET_ROOMID(200, "채팅방 번호를 반환합니다."),
    GET_CHATTING_LIST(200, "모든 채팅방 목록을 반환합니다.");

    private final int code;
    private final String message;
}
