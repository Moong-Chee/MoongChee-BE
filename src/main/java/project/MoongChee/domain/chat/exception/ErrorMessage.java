package project.MoongChee.domain.chat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    NOT_FOUND_CHATROOM(400, "채팅방이 존재하지 않습니다.");

    private final int code;
    private final String message;
}
