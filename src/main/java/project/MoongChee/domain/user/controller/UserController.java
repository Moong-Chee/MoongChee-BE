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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.MoongChee.domain.user.domain.LoginStatus;
import project.MoongChee.domain.user.dto.request.InitRequest;
import project.MoongChee.domain.user.dto.request.SocialLoginRequest;
import project.MoongChee.domain.user.dto.response.SocialLoginResponse;
import project.MoongChee.domain.user.service.ProfileService;
import project.MoongChee.domain.user.service.UserService;
import project.MoongChee.global.common.response.ApiData;

@Tag(name = "USER", description = "유저 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final ProfileService profileService;

    @PostMapping("/login")
    @Operation(summary = "구글 소셜 회원가입 및 로그인")
    public ApiData<SocialLoginResponse> socialLogin(@RequestBody @Valid SocialLoginRequest request) {
        SocialLoginResponse response = userService.authenticate(request.authCode());
        if (response.status() == LoginStatus.LOGIN) {
            return ApiData.response(LOGIN_SUCCESS.getCode(), LOGIN_SUCCESS.getMessage(), response);
        }
        return ApiData.response(REGISTER_SUCCESS.getCode(), REGISTER_SUCCESS.getMessage(), response);
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
