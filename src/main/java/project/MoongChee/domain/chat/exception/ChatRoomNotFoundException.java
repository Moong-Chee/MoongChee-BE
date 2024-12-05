package project.MoongChee.domain.chat.exception;

import static project.MoongChee.domain.chat.exception.ErrorMessage.NOT_FOUND_CHATROOM;

import project.MoongChee.global.common.exception.BaseException;

public class ChatRoomNotFoundException extends BaseException {
    public ChatRoomNotFoundException() {
        super(NOT_FOUND_CHATROOM.getCode(), NOT_FOUND_CHATROOM.getMessage());
    }
}
