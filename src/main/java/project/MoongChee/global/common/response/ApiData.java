package project.MoongChee.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiData<T> {
    private int code;
    private String message;
    private T data;

    // 데이터 X 응답 x
    public static <T> ApiData<T> errorResponse(int code, String message) {
        return new ApiData<>(code, message, null);
    }

    // 데이터 X 응답 생성
    public static <T> ApiData<T> response(int code, String message) {
        return new ApiData<>(code, message, null);
    }

    // 데이터 + 응답 생성
    public static <T> ApiData<T> response(int code, String message, T data) {
        return new ApiData<>(code, message, data);
    }
}