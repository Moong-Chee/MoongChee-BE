package project.MoongChee.domain.user.exception;

import static project.MoongChee.domain.user.exception.ErrorMessage.INVALID_EMAIL;

import project.MoongChee.global.common.exception.BaseException;

public class InvalidEmailException extends BaseException {
    public InvalidEmailException() {
        super(INVALID_EMAIL.getCode(), INVALID_EMAIL.getMessage());
    }
}
