package project.MoongChee.domain.user.exception;

import static project.MoongChee.domain.user.exception.ErrorMessage.USER_NOT_FOUND;

import project.MoongChee.global.common.exception.BaseException;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage());
    }
}
