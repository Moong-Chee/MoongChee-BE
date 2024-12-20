package project.MoongChee.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.MoongChee.domain.post.dto.PostGetDetailResponseDTO;
import project.MoongChee.domain.post.dto.PostRequestDTO;
import project.MoongChee.domain.post.dto.PostResponseDTO;
import project.MoongChee.domain.post.dto.PostUpdateRequestDTO;
import project.MoongChee.domain.post.entity.PostKeyword;
import project.MoongChee.domain.post.entity.TradeType;
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

    @GetMapping//전체 게시물 조회
    @Operation(summary = "전체 게시물 조회")
    public ApiData<List<PostResponseDTO>> getAllPosts() {
        List<PostResponseDTO> postPage = postService.getAllPosts();
        return ApiData.response(PostResponseMessage.POST_GET_ALL_SUCCESS.getCode(),
                PostResponseMessage.POST_GET_ALL_SUCCESS.getMessage(), postPage);
    }

    //게시물 하나 조회
    @GetMapping("/{postId}")
    @Operation(summary = "게시물 하나 조회")
    public ApiData<PostGetDetailResponseDTO> getPostById(@PathVariable Long postId) {
        PostGetDetailResponseDTO post = postService.getPostById(postId);
        return ApiData.response(PostResponseMessage.POST_GET_ONE_SUCCESS.getCode(),
                PostResponseMessage.POST_GET_ONE_SUCCESS.getMessage(), post);
    }

    @GetMapping("/search")//리스트를 통한 구현
    @Operation(summary = "게시물 이름, 키워드를 통한 검색")
    public ApiData<List<PostResponseDTO>> searchPosts(
            @RequestParam(required = false) @Parameter(description = "이름") String name,
            @RequestParam(required = false) @Parameter(description = "키워드")
            PostKeyword keyword,
            @RequestParam(required = false) @Parameter(description = "거래 유형") TradeType tradeType) {
        List<PostResponseDTO> postPage = postService.searchPosts(name, keyword, tradeType);
        return ApiData.response(PostResponseMessage.POST_SEARCH_SUCCESS.getCode(),
                PostResponseMessage.POST_SEARCH_SUCCESS.getMessage(), postPage);
    }

    @PostMapping("/like/{postId}")//관심 게시물 등록
    @Operation(summary = "게시물 관심 등록")
    public ApiData<Void> addLikePost(@PathVariable Long postId, @AuthenticationPrincipal String email) {
        postService.addLikePost(postId, email);
        return ApiData.response(PostResponseMessage.POST_LIKE_SUCCESS.getCode(),
                PostResponseMessage.POST_LIKE_SUCCESS.getMessage());
    }

    @DeleteMapping("/like/{postId}")//관심 게시물 등록 해제
    @Operation(summary = "게시물 관심 등록 해제")
    public ApiData<Void> deleteLikePost(@PathVariable Long postId, @AuthenticationPrincipal String email) {
        postService.deleteLikePost(postId, email);
        return ApiData.response(PostResponseMessage.POST_LIKE_REMOVE_SUCCESS.getCode(),
                PostResponseMessage.POST_LIKE_REMOVE_SUCCESS.getMessage());
    }


}
