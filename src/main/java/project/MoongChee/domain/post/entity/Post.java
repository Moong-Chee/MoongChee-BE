package project.MoongChee.domain.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.MoongChee.domain.user.domain.User;
import project.MoongChee.global.common.domain.BaseTimeEntity;

@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Column(name = "name")
    private String name;

    @Column(name = "profile_image_url")
    private String productImageUrl;

    @Column(name = "product_content")
    private String productContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "keyword")
    private PostKeyword keyword;

    @Column(name = "product_status")
    private String productStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_status")
    @Builder.Default
    private PostStatus postStatus = PostStatus.ACTIVE;//기본값을 설정해서 처음 대여 게시글을 설정하면 ACTIVE 상태가 되도록 한다.

    @Column(name = "return_date")//대여 만료일. 언제까지 대여가능한지
    private LocalDate returnDate;//프론트에서 input type="date"를 사용했다는 가정을 하여 LocalDate 사용

    @Column(name = "rental_price")
    private Integer rentalPrice;

}
