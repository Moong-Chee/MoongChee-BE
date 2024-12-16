package project.MoongChee.domain.chat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    NOT_FOUND_CHATROOM(400, "존재하지 않는 채팅방입니다."),
    DUPLICATE_CHATROOM(400, "중복된 채팅방입니다.");


    private final int code;
    private final String message;
}
