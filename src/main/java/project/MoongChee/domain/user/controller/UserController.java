package project.MoongChee.domain.user.controller;

import static project.MoongChee.domain.user.controller.ResponseMessage.GET_PROFILE_SUCCESS;
import static project.MoongChee.domain.user.controller.ResponseMessage.INIT_PROFILE_SUCCESS;
import static project.MoongChee.domain.user.controller.ResponseMessage.LOGIN_SUCCESS;
import static project.MoongChee.domain.user.controller.ResponseMessage.USER_SAVE_SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.MoongChee.domain.user.domain.LoginStatus;
import project.MoongChee.domain.user.dto.request.UserInitializeRequest;
import project.MoongChee.domain.user.dto.request.UserSocialLoginRequest;
import project.MoongChee.domain.user.dto.response.UserProfileResponse;
import project.MoongChee.domain.user.dto.response.UserSocialLoginResponse;
import project.MoongChee.domain.user.service.UserService;
import project.MoongChee.global.common.response.ApiData;

@Tag(name = "USER")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "구글 소셜 회원가입 및 로그인")
    public ApiData<UserSocialLoginResponse> socialLogin(@RequestBody @Valid UserSocialLoginRequest request) {
        UserSocialLoginResponse response = userService.authenticate(request.authCode());
        if (response.status() == LoginStatus.LOGIN) {
            return ApiData.response(LOGIN_SUCCESS.getCode(), LOGIN_SUCCESS.getMessage(), response);
        }
        return ApiData.response(USER_SAVE_SUCCESS.getCode(), USER_SAVE_SUCCESS.getMessage(), response);
    }

    @PatchMapping("/init")
    @Operation(summary = "최초 로그인시 정보 입력")
    public ApiData<String> initUserProfile(@RequestBody @Valid UserInitializeRequest request,
                                           @AuthenticationPrincipal @Parameter(hidden = true) String email) {// 스웨거에서 해당 정보 입력을 받지 않기 위해 hidden으로 설정
        userService.initProfile(request, email);
        return ApiData.response(INIT_PROFILE_SUCCESS.getCode(), INIT_PROFILE_SUCCESS.getMessage());
    }

    @GetMapping("/profile/{customId}")
    @Operation(summary = "유저 기본 프로필 조회")
    public ApiData<UserProfileResponse> getUserProfile(@PathVariable String customId,
                                                       @AuthenticationPrincipal @Parameter(hidden = true) String email) {
        return ApiData.response(GET_PROFILE_SUCCESS.getCode(), GET_PROFILE_SUCCESS.getMessage(),
                userService.getProfile(customId, email));
    }
}
