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
    private String productImageUrl;

    @NotBlank
    private String productContent;

    @NotNull
    private PostKeyword keyword;

    @NotBlank
    private String productStatus;

    @NotNull
    private PostStatus postStatus;

    @NotNull
    private LocalDate returnDate;

    @NotNull
    private Integer rentalPrice;
}
