package project.MoongChee.domain.chat.exception;

import static project.MoongChee.domain.chat.exception.ErrorMessage.DUPLICATE_CHATROOM;

import project.MoongChee.global.common.exception.BaseException;

public class ChatRoomAlreadyExists extends BaseException {
    public ChatRoomAlreadyExists() {
        super(DUPLICATE_CHATROOM.getCode(), DUPLICATE_CHATROOM.getMessage());
    }
}
