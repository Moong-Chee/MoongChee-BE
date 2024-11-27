package project.MoongChee.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private int code;
    private String message;
    private T data;

    // 데이터 X 응답 x
    public static <T> ResponseDto<T> errorResponse(int code, String message) {
        return new ResponseDto<>(code, message, null);
    }

    // 데이터 X 응답 생성
    public static <T> ResponseDto<T> response(int code, String message) {
        return new ResponseDto<>(code, message, null);
    }

    // 데이터 + 응답 생성
    public static <T> ResponseDto<T> response(int code, String message, T data) {
        return new ResponseDto<>(code, message, data);
    }
}