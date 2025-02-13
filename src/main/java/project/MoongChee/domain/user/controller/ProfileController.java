package project.MoongChee.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.MoongChee.domain.post.controller.PostResponseMessage;
import project.MoongChee.domain.post.dto.MyPostByStatusResponseDTO;
import project.MoongChee.domain.post.dto.PostResponseDTO;
import project.MoongChee.domain.user.dto.response.MyPageResponseDTO;
import project.MoongChee.domain.user.dto.response.MyProfileResponse;
import project.MoongChee.domain.user.dto.response.UserProfileResponse;
import project.MoongChee.domain.user.service.ProfileService;
import project.MoongChee.global.common.response.ApiData;

@Tag(name = "Profile", description = "프로필 관련 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping//마이페이지 앤드포인트
    @Operation(summary = "마이페이지 조회")
    public ApiData<MyPageResponseDTO> geyMyPage(@AuthenticationPrincipal @Parameter(hidden = true) String email) {
        MyPageResponseDTO myPage = profileService.getMyPage(email);
        return ApiData.response(ResponseMessage.MY_PAGE_SUCCESS.getCode(), ResponseMessage.MY_PAGE_SUCCESS.getMessage(),
                myPage);
    }

    @GetMapping("/details")
    @Operation(summary = "내 프로필 상세 조회")
    public ApiData<MyProfileResponse> getMyProfile(@AuthenticationPrincipal @Parameter(hidden = true) String email) {
        return ApiData.response(ResponseMessage.MY_PROFILE_SUCCESS.getCode(),
                ResponseMessage.MY_PROFILE_SUCCESS.getMessage(),
                profileService.getMyProfile(email));
    }

    @GetMapping("/details/{userId}")
    @Operation(summary = "유저 프로필 상세 조회")
    public ApiData<UserProfileResponse> getUserProfileByCustomId(@PathVariable Long userId) {
        return ApiData.response(
                ResponseMessage.USER_PROFILE_SUCCESS.getCode(),
                ResponseMessage.USER_PROFILE_SUCCESS.getMessage(),
                profileService.getUserProfile(userId)
        );
    }

    @GetMapping("/my-like-posts")//관심 게시물 조회
    @Operation(summary = "마이페이지 관심 게시물 조회")
    public ApiData<List<PostResponseDTO>> getLikePosts(@AuthenticationPrincipal String email) {
        List<PostResponseDTO> likes = profileService.getLikePosts(email);
        return ApiData.response(PostResponseMessage.POST_GET_LIKE_SUCCESS.getCode(),
                PostResponseMessage.POST_GET_LIKE_SUCCESS.getMessage(), likes);
    }

    @GetMapping("/my-active-posts")//나의 게시물 중 진행중인 거래 조회
    @Operation(summary = "마이페이지 진행중인 거래 조회")
    public ApiData<List<MyPostByStatusResponseDTO>> getMyActivePosts(@AuthenticationPrincipal String email) {
        List<MyPostByStatusResponseDTO> myActivePosts = profileService.getMyActivePosts(email);
        return ApiData.response(PostResponseMessage.POST_MY_ACTIVE_SUCCESS.getCode(),
                PostResponseMessage.POST_MY_ACTIVE_SUCCESS.getMessage(), myActivePosts);
    }

    @GetMapping("/my-closed-posts")//나의 게시물 중 종료된 거래 조회
    @Operation(summary = "마이페이지 종료된 거래 조회")
    public ApiData<List<MyPostByStatusResponseDTO>> getMyClosedPosts(@AuthenticationPrincipal String email) {
        List<MyPostByStatusResponseDTO> myClosedPosts = profileService.getMyClosedPosts(email);
        return ApiData.response(PostResponseMessage.POST_MY_CLOSED_SUCCESS.getCode(),
                PostResponseMessage.POST_MY_CLOSED_SUCCESS.getMessage(), myClosedPosts);
    }
}
