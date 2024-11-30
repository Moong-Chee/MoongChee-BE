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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.MoongChee.domain.post.dto.PostRequestDTO;
import project.MoongChee.domain.post.dto.PostResponseDTO;
import project.MoongChee.domain.post.dto.PostUpdateRequestDTO;
import project.MoongChee.domain.post.entity.PostKeyword;
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

    //게시물 전체 조회
    /*@GetMapping
    @Operation(summary = "전체 게시물 조회")
    public ApiData<Page<PostResponseDTO>> getAllPosts(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        Page<PostResponseDTO> posts = postService.getAllPosts(pageable);
        return ApiData.response(PostResponseMessage.POST_GETALL_SUCCESS.getCode(),
                PostResponseMessage.POST_GETALL_SUCCESS.getMessage(), posts);
    }*/

    @GetMapping//리스트를 통한 구현. 피그마를 보니 페이지를 나누지 않고 스크롤을 통한 구현이 이루어졌기에 리스트를 통한 구현으로 수정하였습니다.
    @Operation(summary = "전체 게시물 조회")
    public ApiData<List<PostResponseDTO>> getAllPosts() {
        List<PostResponseDTO> postPage = postService.getAllPosts();
        return ApiData.response(PostResponseMessage.POST_GETALL_SUCCESS.getCode(),
                PostResponseMessage.POST_GETALL_SUCCESS.getMessage(), postPage);
    }

    //게시물 하나 조회
    @GetMapping("/{postId}")
    @Operation(summary = "게시물 하나 조회")
    public ApiData<PostResponseDTO> getPostById(@PathVariable Long postId) {
        PostResponseDTO post = postService.getPostById(postId);
        return ApiData.response(PostResponseMessage.POST_GETONE_SUCCESS.getCode(),
                PostResponseMessage.POST_GETONE_SUCCESS.getMessage(), post);
    }

    @GetMapping("/search")//리스트를 통한 구현
    @Operation(summary = "게시물 이름, 키워드를 통한 검색")
    public ApiData<List<PostResponseDTO>> searchPosts(
            @RequestParam(required = false) @Parameter(description = "이름") String name,
            @RequestParam(required = false) @Parameter(description = "키워드")
            PostKeyword keyword) {
        List<PostResponseDTO> postPage = postService.searchPosts(name, keyword);
        return ApiData.response(PostResponseMessage.POST_SEARCH_SUCCESS.getCode(),
                PostResponseMessage.POST_SEARCH_SUCCESS.getMessage(), postPage);
    }
}
