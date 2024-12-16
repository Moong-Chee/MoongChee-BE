package project.MoongChee.domain.chat.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    CHATROOM_CREATE_SUCCESS(200, "채팅방 생성에 성공했습니다."),
    CHATROOM_GET_SUCCESS(200, "하나의 채팅방 조회에 성공했습니다."),
    ROOM_ID_GET_SUCCESS(200, "유저1과 유저2의 채팅방 조회에 성공했습니다."),
    CHATTING_LIST_GET_SUCCESS(200, "모든 채팅방 목록 조회에 성공했습니다."),
    MESSAGE_SEND_SUCCESS(200, "메시지 전송에 성공했습니다.");

    private final int code;
    private final String message;
}
