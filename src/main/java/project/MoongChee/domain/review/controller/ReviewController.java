package project.MoongChee.domain.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import project.MoongChee.domain.review.dto.ReviewRequestDTO;
import project.MoongChee.domain.review.dto.ReviewResponseDTO;
import project.MoongChee.domain.review.service.ReviewService;
import project.MoongChee.global.common.response.ApiData;

@Tag(name = "Review", description = "리뷰 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{postId}")
    @Operation(summary = "리뷰 작성")
    public ApiData<ReviewResponseDTO> createReview(@PathVariable Long postId,
                                                   @RequestPart @Valid ReviewRequestDTO requestDTO,
                                                   @AuthenticationPrincipal String email) {
        ReviewResponseDTO responseDTO = reviewService.createReview(postId, requestDTO, email);
        return ApiData.response(ReviewResponseMessage.REVIEW_CREATE_SUCCESS.getCode(),
                ReviewResponseMessage.REVIEW_CREATE_SUCCESS.getMessage(), responseDTO);
    }
}