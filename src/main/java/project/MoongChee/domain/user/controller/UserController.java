package project.MoongChee.domain.user.controller;

import static project.MoongChee.domain.user.controller.ResponseMessage.INIT_PROFILE_SUCCESS;
import static project.MoongChee.domain.user.controller.ResponseMessage.LOGIN_SUCCESS;
import static project.MoongChee.domain.user.controller.ResponseMessage.REGISTER_SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.MoongChee.domain.user.domain.LoginStatus;
import project.MoongChee.domain.user.dto.request.InitRequest;
import project.MoongChee.domain.user.dto.response.SocialLoginResponse;
import project.MoongChee.domain.user.service.UserService;
import project.MoongChee.global.common.response.ApiData;

@Slf4j
@Tag(name = "USER", description = "유저 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "구글 소셜 회원가입 및 로그인")
    public ApiData<SocialLoginResponse> socialLogin(@RequestParam("code") String authCode) {
        SocialLoginResponse response = userService.authenticate(authCode);
        if (response.status() == LoginStatus.LOGIN) {
            return ApiData.response(LOGIN_SUCCESS.getCode(), LOGIN_SUCCESS.getMessage(), response);
        }
        return ApiData.response(REGISTER_SUCCESS.getCode(), REGISTER_SUCCESS.getMessage(), response);
    }

    @GetMapping("/oauth/google/callback")
    @Operation(summary = "구글 리디렉션 콜백")
    public ResponseEntity<String> googleCallback(@RequestParam("code") String code) {

        log.info("Received Google Authorization Code: {}", code);

        // 프론트로 Authorization Code를 전달
        return ResponseEntity.ok(code);
    }

    @PatchMapping("/init")
    @Operation(summary = "초기 기본정보 입력")
    public ApiData<String> initUserProfile(@RequestPart("request") @Valid InitRequest request,
                                           @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
                                           @AuthenticationPrincipal @Parameter(hidden = true) String email)
            throws IOException {
        userService.initProfile(request, profileImage, email);
        return ApiData.response(INIT_PROFILE_SUCCESS.getCode(), INIT_PROFILE_SUCCESS.getMessage());
    }
}
