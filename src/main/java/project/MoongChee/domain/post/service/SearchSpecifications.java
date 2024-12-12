package project.MoongChee.domain.post.service;

import org.springframework.data.jpa.domain.Specification;
import project.MoongChee.domain.post.entity.Post;
import project.MoongChee.domain.post.entity.PostKeyword;
import project.MoongChee.domain.post.entity.PostStatus;
import project.MoongChee.domain.post.entity.TradeType;

public class SearchSpecifications {
    public static Specification<Post> hasNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Post> hasKeyword(PostKeyword keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("keyword"), keyword);
    }

    public static Specification<Post> hasTradeType(TradeType tradeType) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("tradeType"), tradeType);
    }

    public static Specification<Post> isNotClosed() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("postStatus"), PostStatus.CLOSED);
    }
}
