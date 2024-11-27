package project.MoongChee.global.common.exception;

public class BaseException extends RuntimeException {

    private final int errorCode;

    public BaseException(int errorCode, String message) { // 모든 예외처리는 해당 내용 상속
        super(message);
        this.errorCode = errorCode;
    }
}
