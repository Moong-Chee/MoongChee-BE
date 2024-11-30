package project.MoongChee.domain.post.exception;

import project.MoongChee.global.common.exception.BaseException;

public class NameNotFoundException extends BaseException {
    public NameNotFoundException() {
        super(PostErrorMessage.NAME_NOT_FOUND.getCode(), PostErrorMessage.NAME_NOT_FOUND.getMessage());
    }
}
