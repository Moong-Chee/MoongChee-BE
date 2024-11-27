package project.MoongChee.domain.user.exception;

import static project.MoongChee.domain.user.exception.ErrorMessage.DUPLICATE_CUSTOMID;

import project.MoongChee.global.common.exception.BaseException;

public class DuplicateCustomIdException extends BaseException {
    public DuplicateCustomIdException() {
        super(DUPLICATE_CUSTOMID.getCode(), DUPLICATE_CUSTOMID.getMessage());
    }
}
