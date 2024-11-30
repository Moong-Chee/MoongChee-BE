package project.MoongChee.domain.post.exception;

import project.MoongChee.global.common.exception.BaseException;

public class KeywordNotFoundException extends BaseException {
    public KeywordNotFoundException() {
        super(PostErrorMessage.KEYWORD_NOT_FOUND.getCode(), PostErrorMessage.KEYWORD_NOT_FOUND.getMessage());
    }
}
