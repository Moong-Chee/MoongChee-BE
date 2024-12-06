package project.MoongChee.domain.review.exception;

import project.MoongChee.global.common.exception.BaseException;

public class DuplicateReviewException extends BaseException {
    public DuplicateReviewException() {
        super(ReviewErrorMessage.DUPLICATE_REVIEW.getCode(), ReviewErrorMessage.DUPLICATE_REVIEW.getMessage());
    }
}
