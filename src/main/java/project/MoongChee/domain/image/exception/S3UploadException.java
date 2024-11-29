package project.MoongChee.domain.image.exception;

import project.MoongChee.global.common.exception.BaseException;

public class S3UploadException extends BaseException {
    public S3UploadException() {
        super(ErrorMessage.S3_UPLOAD_FAIL.getCode(), ErrorMessage.S3_UPLOAD_FAIL.getMessage());
    }
}
