package project.MoongChee.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.MoongChee.domain.post.entity.PostKeyword;
import project.MoongChee.domain.post.entity.PostStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String productContent;

    @NotNull
    private PostKeyword keyword;

    @NotNull
    private PostStatus postStatus;//대여 게시물 수정에서는 게시물의 상태를 변경할 수 있게 한다.

    @NotNull
    private LocalDate returnDate;

    @NotNull
    private Integer rentalPrice;
}
