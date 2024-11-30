package project.MoongChee.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.MoongChee.domain.post.dto.PostRequestDTO;
import project.MoongChee.domain.post.dto.PostResponseDTO;
import project.MoongChee.domain.post.dto.PostUpdateRequestDTO;
import project.MoongChee.domain.post.service.PostService;
import project.MoongChee.global.common.response.ApiData;

@RestController
@RequiredArgsConstructor
@Tag(name = "POST")
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    //게시물 생성
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시글 생성")
    public ApiData<PostResponseDTO> createPost(@RequestPart(value = "requestDTO") @Valid PostRequestDTO requestDTO,
                                               @RequestPart(value = "productImages", required = false) List<MultipartFile> productImages,
                                               @AuthenticationPrincipal String email) throws IOException {
        PostResponseDTO response = postService.createPost(requestDTO, productImages, email);
        return ApiData.response(PostResponseMessage.POST_CREATE_SUCCESS.getCode(),
                PostResponseMessage.POST_CREATE_SUCCESS.getMessage(), response);
    }

    //게시물 수정
    @PatchMapping(value = "/{postId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시글 수정")
    public ApiData<PostResponseDTO> updatePost(@PathVariable Long postId,
                                               @RequestPart(value = "requestDTO") @Valid PostUpdateRequestDTO requestDTO,
                                               @RequestPart(value = "productImages", required = false) List<MultipartFile> productImages,
                                               @AuthenticationPrincipal String email) throws IOException {
        PostResponseDTO response = postService.updatePost(postId, requestDTO, productImages, email);
        return ApiData.response(PostResponseMessage.POST_UPDATE_SUCCESS.getCode(),
                PostResponseMessage.POST_UPDATE_SUCCESS.getMessage(), response);
    }
}
