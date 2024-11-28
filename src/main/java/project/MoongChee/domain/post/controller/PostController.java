package project.MoongChee.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.MoongChee.domain.post.dto.PostRequestDTO;
import project.MoongChee.domain.post.dto.PostResponseDTO;
import project.MoongChee.domain.post.dto.PostUpdateRequestDTO;
import project.MoongChee.domain.post.service.PostService;
import project.MoongChee.global.common.response.ApiData;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    //게시물 생성
    @PostMapping
    public ApiData<PostResponseDTO> createPost(@RequestBody @Valid PostRequestDTO requestDTO,
                                               @AuthenticationPrincipal String email) {
        PostResponseDTO response = postService.createPost(requestDTO, email);
        return ApiData.response(PostResponseMessage.POST_CREATE_SUCCESS.getCode(),
                PostResponseMessage.POST_CREATE_SUCCESS.getMessage(), response);
    }

    @PatchMapping("/{postId}")
    public ApiData<PostResponseDTO> updatePost(@PathVariable Long postId,
                                               @RequestBody @Valid PostUpdateRequestDTO requestDTO,
                                               @AuthenticationPrincipal String email) {
        PostResponseDTO response = postService.updatePost(postId, requestDTO, email);
        return ApiData.response(PostResponseMessage.POST_UPDATE_SUCCESS.getCode(),
                PostResponseMessage.POST_UPDATE_SUCCESS.getMessage(), response);
    }
}
