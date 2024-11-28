package project.MoongChee.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.MoongChee.domain.post.entity.PostKeyword;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String productImageUrl;

    @NotBlank
    private String productContent;

    @NotBlank
    private PostKeyword keyword;

}
