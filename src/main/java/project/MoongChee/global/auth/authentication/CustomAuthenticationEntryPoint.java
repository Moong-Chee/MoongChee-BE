package project.MoongChee.global.auth.authentication;

import static project.MoongChee.global.auth.exception.ErrorMessage.INVALID_TOKEN;
import static project.MoongChee.global.auth.exception.ErrorMessage.UNAUTHORIZED;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import project.MoongChee.global.common.response.ApiData;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final String LOG_FORMAT = "Class : {}, Code : {}, Message : {}";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        Integer exceptionCode = (Integer) request.getAttribute("jwtException");
        log.error("Authentication failed. Request URI: {}, Exception Code: {}, Exception Message: {}",
                request.getRequestURI(), exceptionCode, authException.getMessage());

        /*
         * exceptionCode가 null이 아니라면 토큰 유효성 검사를 실패한 것이기 때문에 따로 처리
         * exceptionCode가 null이라면 인증 정보가 없는 것이기 때문에 따로 처리
         */
        if (exceptionCode != null) {
            if (exceptionCode == INVALID_TOKEN.getCode()) {
                setResponse(response, INVALID_TOKEN.getCode(), INVALID_TOKEN.getMessage());
            } else {
                setResponse(response, UNAUTHORIZED.getCode(), "알 수 없는 인증 오류");
            }
        } else {
            setResponse(response, UNAUTHORIZED.getCode(), UNAUTHORIZED.getMessage());
        }
    }

    private void setResponse(HttpServletResponse response, int code, String message) throws IOException {
        response.setStatus(code);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = new ObjectMapper().writeValueAsString(ApiData.errorResponse(code, message));
        response.getWriter().write(json);
    }
}
