package project.MoongChee.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.MoongChee.domain.post.entity.PostKeyword;
import project.MoongChee.domain.post.entity.TradeType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDTO {
    @NotNull
    private TradeType tradeType;

    @NotBlank
    private String name;

    @NotBlank
    private String productContent;

    @NotNull
    private PostKeyword keyword;

    @NotNull
    private LocalDate date;

    @NotNull
    private Integer price;

}
