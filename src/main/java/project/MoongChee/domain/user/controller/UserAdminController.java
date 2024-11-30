package project.MoongChee.domain.user.controller;

import static project.MoongChee.domain.user.controller.ResponseMessage.USER_ACCEPT_SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.MoongChee.domain.user.domain.User;
import project.MoongChee.domain.user.service.UserGetService;
import project.MoongChee.domain.user.service.UserUpdateService;
import project.MoongChee.global.common.response.ApiData;

@Tag(name = "UserAdmin", description = "유저 어드민 컨트롤러")
@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class UserAdminController {
    private final UserGetService userGetService;
    private final UserUpdateService userUpdateService;

    @PatchMapping("/accept")
    @Operation(summary = "가입 신청 승인")
    public ApiData<Void> accept(@RequestParam Long userId) {
        User user = userGetService.find(userId);
        if (user.isInactive()) {
            userUpdateService.accept(user);
        }
        return ApiData.response(USER_ACCEPT_SUCCESS.getCode(), USER_ACCEPT_SUCCESS.getMessage());
    }
}
