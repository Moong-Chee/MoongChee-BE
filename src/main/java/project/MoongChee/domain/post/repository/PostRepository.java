package project.MoongChee.domain.post.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.MoongChee.domain.post.entity.Post;
import project.MoongChee.domain.post.entity.PostKeyword;
import project.MoongChee.domain.post.entity.PostStatus;
import project.MoongChee.domain.post.entity.TradeType;
import project.MoongChee.domain.user.domain.User;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostIdAndAuthor(Long postId, User author);

    //게시물 검색을 querydsl을 사용할지 jpql을 사용할지 고민하였는데 제가 querydsl에 아직 익숙하지 못해 jpql을 사용하였습니다.
    @Query("SELECT p FROM Post p WHERE " +
            "p.postStatus!='CLOSED'AND" +
            "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:keyword IS NULL OR p.keyword = :keyword) AND" +
            ":tradeType IS NULL OR p.tradeType=:tradeType")
    List<Post> searchPosts(@Param("name") String name, @Param("keyword") PostKeyword keyword,
                           @Param("tradeType") TradeType tradeType);

    List<Post> findByPostStatusNot(PostStatus status);
}
