package project.MoongChee.domain.post.exception;

import project.MoongChee.global.common.exception.BaseException;

public class PostNotLikedException extends BaseException {
    public PostNotLikedException() {
        super(PostErrorMessage.POST_NOT_FOUND.getCode(), PostErrorMessage.POST_NOT_FOUND.getMessage());
    }
}
